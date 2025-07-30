package com.github.nenidan.ne_ne_challenge.domain.payment.infrastructure.client.toss.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class TossPaymentsConfig {

    @Value("${toss.payments.secret-key}")
    private String secretKey;

    @Value("${toss.payments.client-key}")
    private String clientKey;

    @Value("${toss.payments.base-url}")
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl != null ? baseUrl.trim() : null;
    }
}
