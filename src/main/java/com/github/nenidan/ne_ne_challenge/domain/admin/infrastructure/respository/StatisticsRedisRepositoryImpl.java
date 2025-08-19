package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.respository;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.repository.StatisticsRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StatisticsRedisRepositoryImpl implements StatisticsRedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String PREFIX = "statistics:";

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String type, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(PREFIX + type);
        return clazz.cast(value);
    }

}
