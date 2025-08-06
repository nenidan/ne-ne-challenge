package com.github.nenidan.ne_ne_challenge.global.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.github.benmanes.caffeine.cache.Caffeine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableCaching
public class CaffeineConfig {

    @Bean
    public Caffeine<Object, Object> caffeine() {
        return Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(50)
            .removalListener((key, value, cause) -> {
                log.info("캐시 삭제 : {}", key);
            })
            .recordStats();
    }

    /**
     * {@code CaffeineCacheManager} Bean을 생성하여 Spring의 캐시 매니저로 등록합니다.
     *
     * <p>전달받은 {@link Caffeine} 객체를 이용하여 캐시 설정(예: 만료 시간, 최대 크기 등)을 지정합니다.
     * 이를 통해 {@code @Cacheable}, {@code @CachePut}, {@code @CacheEvict} 어노테이션을 사용하는
     * Spring Cache 추상화에 Caffeine 기반 캐시 전략을 적용할 수 있습니다.
     *
     * @param caffeine 캐시의 만료 시간, 최대 용량 등 세부 설정을 포함하는 {@link Caffeine} 객체
     * @return 설정이 반영된 {@link CaffeineCacheManager} 인스턴스
     */
    @Bean
    @Primary
    public CaffeineCacheManager caffeineCacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }
}
