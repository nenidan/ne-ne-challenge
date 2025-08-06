package com.github.nenidan.ne_ne_challenge.domain.user.application.service;

import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.UserResult;
import com.github.nenidan.ne_ne_challenge.domain.user.application.mapper.UserMapper;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CachedUserService {

    private final UserService userService;

    @Cacheable(value = "profileSearch", key = "'default'")
    public List<UserResult> searchProfiles() {
        return userService.searchProfiles("", 10, "")
                .stream().map(UserMapper::toDto).toList();
    }
}
