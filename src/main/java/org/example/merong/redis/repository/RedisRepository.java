package org.example.merong.redis.repository;

import org.example.merong.redis.vo.BlackList;
import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<BlackList, String> {
}

