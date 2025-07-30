package com.github.nenidan.ne_ne_challenge.domain.shop.global;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.github.nenidan.ne_ne_challenge.domain.user.dto.response.UserResponse;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserException;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;

import jakarta.annotation.PostConstruct;

@Component
public class UserRestClientImpl implements UserRestClient {

    @Value("${external.base-url}")
    private String baseUrl;
    private RestClient restClient;

    @PostConstruct
    public void init(){
        restClient = RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    @Override
    public UserResponse getUser(Long userId) {

        ApiResponse<UserResponse> apiResponse = restClient.get()
            .uri("/profiles/{userId}", userId)
            .retrieve()
            .body(new ParameterizedTypeReference<>() {});

        if (apiResponse == null) {
            throw new UserException(UserErrorCode.USER_NOT_FOUND);
        }

        return apiResponse.getData();
    }
}
