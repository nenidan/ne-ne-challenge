package com.github.nenidan.ne_ne_challenge.global.client.user;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
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

    @Override
    public List<UserResponse> getUserAll() {
        return restClient.get()
                .uri("/internal/profiles")
                .retrieve()
                .body(new ParameterizedTypeReference<List<UserResponse>>() {});
    }

    @Override
    public List<UserResponse> getUserListByidList(List<Long> userIdList) {
        return restClient.get()
                .uri("/internal/profiles/bulk")
                .retrieve()
                .body(new ParameterizedTypeReference<List<UserResponse>>() {});
    }
}
