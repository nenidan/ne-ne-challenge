package com.github.nenidan.ne_ne_challenge.global.client.user;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.github.nenidan.ne_ne_challenge.global.client.user.dto.UserResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserClientImpl implements UserClient {

    private final RestClient restClient;

    @Override
    public UserResponse getUserById(long userId) {
        return restClient.get()
                .uri("/internal/profiles/" + userId)
                .retrieve()
                .body(UserResponse.class);
    }
}
