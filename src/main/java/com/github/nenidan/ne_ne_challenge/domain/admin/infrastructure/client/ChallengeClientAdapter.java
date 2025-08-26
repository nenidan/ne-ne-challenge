package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.github.nenidan.ne_ne_challenge.domain.admin.application.client.ChallengeClientPort;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out.ChallengeDto;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out.ChallengeUserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChallengeClientAdapter implements ChallengeClientPort {

    @Value("${external.base-url}")
    private String BASE_URL;

    private static final String ALL_CHALLENGES_ENDPOINT = "/internal/statistics/challenges";
    private static final String ALL_CHALLENGE_USERS_ENDPOINT = "/internal/statistics/participants";
    private final RestClient restClient;

    @Override
    public List<ChallengeDto> getAllChallenges() {
        try {
            return restClient.get()
                    .uri(BASE_URL + ALL_CHALLENGES_ENDPOINT)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<ChallengeDto>>() {
                    });
        }catch (RestClientException e) {
                log.error("외부 챌린지 목록 조회 실패", e);
                return List.of(); // 또는 null, 또는 custom 예외 throw
            }

    }

    @Override
    public List<ChallengeUserDto> getAllChallengeUsers() {
        try {
            return restClient.get()
                    .uri(BASE_URL + ALL_CHALLENGE_USERS_ENDPOINT)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<ChallengeUserDto>>() {
                    });
        }catch (RestClientException e) {
            log.error("외부 챌린지 유저목록 조회 실패", e);
            return List.of(); // 또는 null, 또는 custom 예외 throw
        }
    }
}

