package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.client;

import com.github.nenidan.ne_ne_challenge.domain.admin.application.client.PaymentClientPort;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out.PaymentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentClientAdapter implements PaymentClientPort {

    @Value("${external.base-url}")
    private String BASE_URL;

    private final RestClient restClient;

    private static final String ALL_PAYMENTS_ENDPOINT = "/internal/statistics/payments";

    @Override
    public List<PaymentDto> getAllPayments() {
        try {
            return restClient.get()
                    .uri(BASE_URL + ALL_PAYMENTS_ENDPOINT)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<PaymentDto>>() {
                    });
        }catch (RestClientException e) {
            log.error("외부 결제 목록 조회 실패", e);
            return List.of(); // 또는 null, 또는 custom 예외 throw
        }

    }


}
