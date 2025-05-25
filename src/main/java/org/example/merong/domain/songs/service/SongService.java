package org.example.merong.domain.songs.service;

import java.time.Duration;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.merong.domain.searches.entity.Search;
import org.example.merong.domain.searches.service.SearchService;
import org.example.merong.domain.songs.dto.request.SongRequestDto;
import org.example.merong.domain.songs.dto.request.SongSearchRequestParamDto;
import org.example.merong.domain.songs.dto.request.SongUpdateDto;
import org.example.merong.domain.songs.dto.response.SongResponseDto;
import org.example.merong.domain.songs.dto.response.SongResponseDto.find;
import org.example.merong.domain.songs.entity.Song;
import org.example.merong.domain.songs.exception.SongException;
import org.example.merong.domain.songs.exception.SongsExceptionCode;
import org.example.merong.domain.songs.repository.SongRepository;
import org.example.merong.domain.songs.repository.SongSearch;
import org.example.merong.domain.user.entity.User;
import org.example.merong.domain.user.exception.UserException;
import org.example.merong.domain.user.exception.UserExceptionCode;
import org.example.merong.domain.user.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SongService {

    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final SongSearch songSearch;
    private final SearchService searchService;
    private final RedisTemplate<Object, Object> redisTemplate;
//    private final SongService self;

    // 1. 노래 등록
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


    // 키워드 검색 v1
    public Page<SongResponseDto.find> searchByKeywordLike(SongSearchRequestParamDto songSearchRequestParamDto) {

        if(songSearchRequestParamDto.getTitle() != null && !songSearchRequestParamDto.getTitle().isBlank()){searchService.saveKeyword(songSearchRequestParamDto.getTitle());}

        if(songSearchRequestParamDto.getSinger() != null && !songSearchRequestParamDto.getSinger().isBlank()){searchService.saveKeyword(songSearchRequestParamDto.getSinger());}

        if(songSearchRequestParamDto.getGenre() != null){searchService.saveKeyword(songSearchRequestParamDto.getGenre().toString());}

        return searchService.getSearchResult(songSearchRequestParamDto);
    }


    // 키워드 검색 v2
    public Page<SongResponseDto.find> searchByKeywordLikeV2(SongSearchRequestParamDto paramDto){

        if(paramDto.getTitle() != null && !paramDto.getTitle().isBlank()){searchService.addSearchKeywordRank(paramDto.getTitle());}

        if(paramDto.getSinger() != null && !paramDto.getSinger().isBlank()){searchService.addSearchKeywordRank(paramDto.getSinger());}

        if(paramDto.getGenre() != null){searchService.addSearchKeywordRank(paramDto.getGenre().toString());}


        return searchService.getSearchResult(paramDto);
    }

    public void increaseViewCount(Long id, Long songId) {
        String viewUserKey = "view:songId:" + songId + "userId:" + id;
        String viewCountKey = "view:songId:" + songId;

        Boolean exists = redisTemplate.hasKey(viewUserKey);

        if(Boolean.FALSE.equals(exists)){
            redisTemplate.opsForValue().set(viewUserKey, "1", Duration.ofMinutes(30));
            redisTemplate.opsForValue().increment(viewCountKey);
        }

    }

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정
    public void resetSongViewCounts() {
        Set<Object> keys = redisTemplate.keys("view:songId:*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    // 단건 검색
    public SongResponseDto.Get findById(Long songId) {

        Song findSong = songRepository.findByIdOrElseThrow(songId);

        return new SongResponseDto.Get(findSong);
    }
}

