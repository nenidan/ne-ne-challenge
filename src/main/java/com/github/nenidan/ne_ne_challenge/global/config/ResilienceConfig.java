package com.github.nenidan.ne_ne_challenge.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryRegistry;

@Configuration
public class ResilienceConfig {

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        return CircuitBreakerRegistry.ofDefaults(); // YML 설정 자동 로드
    }

    @Bean
    public RetryRegistry retryRegistry() {
        return RetryRegistry.ofDefaults(); // YMl 설정 자동 로드
    }
}
