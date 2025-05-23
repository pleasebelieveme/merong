package org.example.merong.domain.songs.service;

import lombok.RequiredArgsConstructor;
import org.example.merong.domain.songs.entity.Song;
import org.example.merong.domain.songs.repository.SongRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SongCacheService {

    private final RedisTemplate<String, String> redisTemplate;
    private final SongRepository songRepository;

    private static final String PLAY_COUNT_KEY_PREFIX = "song:playCount:";

    // 재생수 증가
    public Long increasePlayCount(Long songId) {
        String key = PLAY_COUNT_KEY_PREFIX + songId;

        if(Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.opsForValue().set(key, "1");
            redisTemplate.expireAt(key, getMidnight());
            return 1L;
        }

        Long count = redisTemplate.opsForValue().increment(key);
        return count != null ? count : 0;
    }

    // 재생수 확인
    public Long getPlayCount(Long songId) {
        String key = PLAY_COUNT_KEY_PREFIX + songId;
        String value = redisTemplate.opsForValue().get(key);
        return value != null ? Long.parseLong(value) : 0L;
    }

    // 자정 계산
    public Date getMidnight() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = now.plusDays(1).toLocalDate().atStartOfDay();
        return Date.from(midnight.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Scheduled(cron = "0 */1 * * * *") // 매 분 0초에 1회 실행
    public void flushPlayCountToDB() {
        List<Song> allSongs = songRepository.findAll();
        for(Song song : allSongs) {
            String key = PLAY_COUNT_KEY_PREFIX + song.getId();
            String countStr = redisTemplate.opsForValue().get(key);
            if (countStr != null) {
                Long count = Long.parseLong(countStr);
                song.updatePlayCounts(count);
                songRepository.save(song);

            }
        }
    }
}
