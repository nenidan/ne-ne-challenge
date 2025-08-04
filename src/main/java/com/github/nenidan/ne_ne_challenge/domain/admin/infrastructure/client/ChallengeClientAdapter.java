package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.client;

import com.github.nenidan.ne_ne_challenge.domain.admin.application.client.ChallengeClientPort;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out.ChallengeDto;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out.ChallengeUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChallengeClientAdapter implements ChallengeClientPort {

    @Value("${external.base-url}")
    private String BASE_URL;
    private RestClient restClient;

    private static final String ALL_CHALLENGES_ENDPOINT = "/challenges";
    private static final String ALL_CHALLENGE_USERS_ENDPOINT = "/challengeUsers";

    @Override
    public List<ChallengeDto> getAllChallenges() {
        return restClient.get()
                .uri(BASE_URL + ALL_CHALLENGES_ENDPOINT)
                .retrieve()
                .body(new ParameterizedTypeReference<List<ChallengeDto>>() {});
    }

    @Override
    public List<ChallengeUserDto> getAllChallengeUsers() {
        return restClient.get()
                .uri(BASE_URL + ALL_CHALLENGE_USERS_ENDPOINT)
                .retrieve()
                .body(new ParameterizedTypeReference<List<ChallengeUserDto>>() {});
    }
}

