package com.github.nenidan.ne_ne_challenge.global.client.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.github.nenidan.ne_ne_challenge.global.client.order.dto.OrderStatisticsResponse;

import jakarta.annotation.PostConstruct;

@Component
public class OrderRestClientImpl implements OrderRestClient {

    @Value("${external.base-url}")
    private String baseUrl;

    private final static String ALL_ORDERS_ENDPOINT = "/internal/statistics/orders";

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
    public List<OrderStatisticsResponse> getAllOrders() {
        return restClient.get()
            .uri(ALL_ORDERS_ENDPOINT)
            .retrieve()
            .body(new ParameterizedTypeReference<>() {
            });
    }
}
