package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.external.point;

import com.github.nenidan.ne_ne_challenge.domain.user.application.client.point.PointClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PointClientImpl implements PointClient {

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
    public void addPointWallet(Long userId) {
        restClient.post()
                .uri(uriBuilder -> uriBuilder.path("/internal/points/wallet")
                        .queryParam("userId", userId)
                        .build()
                )
                .retrieve()
                .toBodilessEntity();
    }
}
