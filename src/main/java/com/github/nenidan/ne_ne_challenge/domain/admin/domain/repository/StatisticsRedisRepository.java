package com.github.nenidan.ne_ne_challenge.domain.admin.domain.repository;

public interface StatisticsRedisRepository {
    <T> T get(String type, Class<T> clazz);
}
