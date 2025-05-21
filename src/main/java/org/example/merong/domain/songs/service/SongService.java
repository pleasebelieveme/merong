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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SongService {

    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;

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
        songRepository.save(song);

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

    // 5. 노래 검색
    public List<SongResponseDto.Get> search(String keyword) {
        recordSearchKeyword(keyword); // 캐시 여부 상관없이 무조건 실행
        return getSearchResults(keyword); // 캐시 적용 대상
    }

    // 인기 검색어 기록 (Redis: ZSet 점수 증가)
    // : 캐시 적중 시 인기 검색어 기록이 누락되어 메서드 분리
    private void recordSearchKeyword(String keyword) {
        redisTemplate.opsForZSet().incrementScore("popular_keywords", keyword, 1);
    }

    // 검색 결과 캐시 (Caffeine + @Cacheable)
    @Cacheable(value = "songSearchCache", key = "#keyword")
    public List<SongResponseDto.Get> getSearchResults(String keyword) {
        return songRepository.searchByKeyword(keyword)
                .stream().map(SongResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 인기 검색어 조회 (Redis: ZSet 내 상위 10)
    public List<String> getPopularKeywords() {
        return redisTemplate.opsForZSet().reverseRange("popular_keywords", 0, 9)
                .stream().map(Object::toString).collect(Collectors.toList());
    }
}
