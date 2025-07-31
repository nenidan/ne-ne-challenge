package com.github.nenidan.ne_ne_challenge.global.client.user;

import com.github.nenidan.ne_ne_challenge.global.client.user.dto.UserResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UserClientImpl implements UserClient {

    @Value("${external.base-url}")
    private String baseUrl;

    private RestClient restClient;

    @PostConstruct
    public void init() {
        restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }


    @Override
    public UserResponse getUserById(long userId) {
        return restClient.get()
                .uri("/internal/profiles/" + userId)
                .retrieve()
                .body(UserResponse.class);
    }
}
