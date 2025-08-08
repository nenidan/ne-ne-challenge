package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.github.nenidan.ne_ne_challenge.domain.admin.application.client.PointClientPort;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out.PointDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PointClientAdapter implements PointClientPort {
    private static final String ALL_PAYMENTS_ENDPOINT = "/internal/statistics/pointTransactions";
    private final RestClient restClient;
    @Value("${external.base-url}")
    private String BASE_URL;

    @Override
    public List<PointDto> getAllPoint() {
        try {
            return restClient.get()
                    .uri(BASE_URL + ALL_PAYMENTS_ENDPOINT)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<PointDto>>() {
                    });
        }catch (RestClientException e) {
            log.error("외부 결제 목록 조회 실패", e);
            return List.of(); // 또는 null, 또는 custom 예외 throw
        }

    }
}
