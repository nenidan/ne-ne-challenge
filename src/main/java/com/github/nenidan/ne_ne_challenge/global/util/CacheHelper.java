package com.github.nenidan.ne_ne_challenge.global.util;

import java.util.function.Supplier;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CacheHelper {

    private final CacheManager cacheManager;

    public <T> T getOrLoad(String cacheName, String key, Supplier<T> fallback) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) throw new IllegalStateException("No such cache: " + cacheName);

        // 1. 캐시에서 조회
        T value = cache.get(key, (Class<T>) Object.class);
        if (value != null) return value;

        // 2. fallback 함수 실행
        T loaded = fallback.get();

        // 3. 캐시에 저장
        cache.put(key, loaded);

        return loaded;
    }
}
