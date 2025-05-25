package org.example.merong.redis.service;

import lombok.RequiredArgsConstructor;
import org.example.merong.redis.dto.RedisToken;
import org.example.merong.redis.repository.RedisRepository;
import org.example.merong.redis.vo.BlackList;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisService {

    private final RedisRepository redisRepository;

    public void addBlackListToken(RedisToken dto) {

        redisRepository.save(BlackList.from(dto));
    }

    public boolean isBlackListed(String accessToken) {

        return redisRepository.existsById(accessToken);
    }
}
