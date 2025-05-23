package org.example.merong.domain.songs.service;

import lombok.RequiredArgsConstructor;
import org.example.merong.domain.songs.repository.SongRepository;
import org.example.merong.domain.songs.dto.request.SongRequestDto;
import org.example.merong.domain.songs.dto.request.SongUpdateDto;
import org.example.merong.domain.songs.dto.response.SongResponseDto;
import org.example.merong.domain.songs.entity.Song;
import org.example.merong.domain.songs.exception.SongException;
import org.example.merong.domain.songs.exception.SongsExceptionCode;
import org.example.merong.domain.user.entity.User;
import org.example.merong.domain.user.exception.UserException;
import org.example.merong.domain.user.exception.UserExceptionCode;
import org.example.merong.domain.user.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SongService {

    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final SongSearchService songSearchService;

    // 1. 노래 등록
    @CacheEvict(value = "songSearchCache", allEntries = true)
    public SongResponseDto.Create createSong(Long userId, SongRequestDto dto) {

        User currentUser = userRepository.findById(userId).orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_FOUND));

        Song song = songRepository.save(new Song(currentUser, dto));

        return new SongResponseDto.Create(song);
    }

    // 2. 내 노래 전체 조회
    @Transactional(readOnly = true)
    public List<SongResponseDto.Get> getSongs(Long userId) {

        User currentUser = userRepository.findById(userId).orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_FOUND));

        return currentUser.getSongs().stream().map(SongResponseDto.Get::new).collect(Collectors.toList());

    }

    // 3. 노래 수정
    @CacheEvict(value = "songSearchCache", allEntries = true)
    public SongResponseDto.Update updateSong(Long userId, Long songId, SongUpdateDto dto) {

        User currentUser = userRepository.findById(userId).orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_FOUND));
        Song song = songRepository.findByIdOrElseThrow(songId);

        // 검증
        if (!currentUser.getId().equals(song.getUser().getId())) {
            throw new SongException(SongsExceptionCode.SONG_OWNERSHIP_EXCEPTION);
        }

        song.updateSong(dto);

        return new SongResponseDto.Update(song);

    }

    // 4. 노래 삭제
    @CacheEvict(value = "songSearchCache", allEntries = true)
    public void deleteSong(Long userId, Long songId) {

        User currentUser = userRepository.findById(userId).orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_FOUND));
        Song song = songRepository.findByIdOrElseThrow(songId);

        // 검증
        if (!currentUser.getId().equals(song.getUser().getId())) {
            throw new SongException(SongsExceptionCode.SONG_OWNERSHIP_EXCEPTION);
        }

        songRepository.delete(song);

    }

    // 5. 노래 검색 - 분리된 캐시 처리
    public List<SongResponseDto.Get> search(String keyword) {
        String cleanedKeyword = keyword == null ? "" : keyword.trim().replaceAll("\\s+", ""); // 캐시 키 정제
        recordSearchKeyword(cleanedKeyword);
        return songSearchService.getSearchResults(cleanedKeyword); // 프록시 경유 → 캐시 작동
    }

    // 인기 검색어 기록 (Redis: ZSet 점수 증가)
    // : 캐시 적중 시 인기 검색어 기록이 누락되어 메서드 분리
    private void recordSearchKeyword(String keyword) {
        redisTemplate.opsForZSet().incrementScore("popular_keywords", keyword, 1);
    }

    // 인기 검색어 조회 (Redis: ZSet 내 상위 10)
    public List<String> getPopularKeywords() {
        return redisTemplate.opsForZSet().reverseRange("popular_keywords", 0, 9)
                .stream().map(Object::toString).collect(Collectors.toList());
    }

    // 조회수 증가
    public void incrementViewCount(Long songId, Long userId) {
        String userViewKey = "view:song:" + songId + ":user:" + userId;
        String viewCountKey = "view:song:" + songId;

        // 어뷰징 방지: 유저가 이미 조회했다면 무시
        Boolean alreadyViewed = redisTemplate.hasKey(userViewKey);
        if (Boolean.FALSE.equals(alreadyViewed)) {
            // 유저 조회 기록 TTL - 어뷰징 방지용
            redisTemplate.opsForValue().set(userViewKey, "1", Duration.ofHours(1));

            // 조회수 key 에도 하루 TTL 부여
            redisTemplate.expire(viewCountKey, Duration.ofDays(1));
        }
    }

    // 자정 리셋 기능
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정
    public void resetViewCounts() {
        Set<String> keys = redisTemplate.keys("view:song:*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
            System.out.println("🎯 조회수 캐시 자정 리셋 완료: " + keys.size() + "개");
        }
    }

    // 6. 단건 노래 검색
    @Transactional(readOnly = true)
    public SongResponseDto.Get getSong(Long songId) {
        Song song = songRepository.findByIdOrElseThrow(songId);
        return new SongResponseDto.Get(song);
    }




}
