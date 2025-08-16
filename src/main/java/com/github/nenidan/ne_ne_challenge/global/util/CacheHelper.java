package com.github.nenidan.ne_ne_challenge.global.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheHelper {

    private final CacheManager cacheManager;

    private static final long TIMEOUT_MS = 100;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public <T> T getOrLoad(String cacheName, String key, Supplier<T> fallback) {
        try {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache == null) {
                log.warn("No such cache '{}'. Fallback to DB.", cacheName);
                return fallback.get();
            }

            // 1. 캐시에서 조회 (100ms 안에만 시도)
            Future<T> future = executor.submit(() -> cache.get(key, (Class<T>) Object.class));
            T value;
            try {
                value = future.get(TIMEOUT_MS, TimeUnit.MILLISECONDS);
            } catch (TimeoutException e) {
                log.warn("Redis read took longer than {}ms. Using fallback.", TIMEOUT_MS);
                future.cancel(true);
                return fallback.get();
            }
            if (value != null)
                return value;

            // 2. fallback 함수 실행
            T loaded = fallback.get();

            // 3. 캐시에 저장
            cache.put(key, loaded);

            return loaded;
        } catch (RedisConnectionFailureException e) {
            log.error("Redis connection failed. Fallback to DB. cause={}", e.getMessage());
            return fallback.get();
        } catch (Exception e) {
            log.error("Cache error. Fallback to DB. cause={}", e.getMessage());
            return fallback.get();
        }
    }
}
