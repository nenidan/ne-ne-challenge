package com.github.nenidan.ne_ne_challenge.domain.user.application.service;

import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.UserResult;
import com.github.nenidan.ne_ne_challenge.domain.user.application.mapper.UserMapper;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CachedUserService {

    private final UserService userService;

    /* 프로필 검색 캐싱 */

    @Cacheable(value = "profileSearch", key = "'default'")
    public List<UserResult> searchProfiles() {
        return userService.searchProfiles("", 10, "")
                .stream().map(UserMapper::toDto).toList();
    }

    @CachePut(value = "profileSearch", key = "'default'")
    public List<UserResult> refreshSearchProfiles() {
        evictSearchProfiles();
        return userService.searchProfiles("", 10, "")
                .stream().map(UserMapper::toDto).toList();
    }

    @CacheEvict(value = "profileSearch", key = "'default'")
    public void evictSearchProfiles() {
        // 내용 없음 - 캐시 삭제
    }


    /* 통계용 프로필 조회 캐싱 */

    @Cacheable(value = "statistics", key = "'profileAll'")
    public List<UserResult> getProfileAll() {
        return userService.getProfiles().stream().map(UserMapper::toDto).toList();
    }

    @CachePut(value = "statistics", key = "'profileAll'")
    public List<UserResult> refreshProfileAll() {
        evictProfileAll();
        return userService.getProfiles().stream().map(UserMapper::toDto).toList();
    }

    @CacheEvict(value = "statistics", key = "'profileAll'")
    public void evictProfileAll() {
        // 내용 없음 - 캐시 삭제
    }

}
