package com.github.nenidan.ne_ne_challenge.global.client.stock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.github.nenidan.ne_ne_challenge.global.client.stock.dto.StockRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import jakarta.annotation.PostConstruct;

@Component
public class StockRestClientImpl implements StockRestClient {

    @Value("${external.base-url}")
    private String baseUrl;

    private static final String STOCK_DECREASE_ENDPOINT = "/internal/products/{productId}/stocks/confirm";
    private static final String STOCK_RESERVATIONS_DECREASE_ENDPOINT = "/internal/products/{id}/stocks";
    private static final String STOCK_RESTORE_ENDPOINT = "/internal/stocks/{productId}/confirm";
    private static final String STOCK_RESERVATIONS_RESTORE_ENDPOINT = "/internal/stocks/{productId}";

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
    public void decreaseStock(ProductId productId, int quantity) {
        restClient.patch()
            .uri(STOCK_DECREASE_ENDPOINT, productId.getValue())
            .body(new StockRequest(quantity))
            .retrieve()
            .toBodilessEntity();
    }

    @Override
    public void decreaseReservedStock(ProductId productId, int quantity) {
        restClient.patch()
            .uri(STOCK_RESERVATIONS_DECREASE_ENDPOINT, productId.getValue())
            .body(new StockRequest(quantity))
            .retrieve()
            .toBodilessEntity();
    }

    @Override
    public void restoreStock(ProductId productId, int quantity) {
        restClient.patch()
            .uri(STOCK_RESTORE_ENDPOINT, productId.getValue())
            .body(new StockRequest(quantity))
            .retrieve()
            .toBodilessEntity();
    }

    @Override
    public void restoreReservedStock(ProductId productId, int quantity) {
        restClient.patch()
            .uri(STOCK_RESERVATIONS_RESTORE_ENDPOINT, productId.getValue())
            .body(new StockRequest(quantity))
            .retrieve()
            .toBodilessEntity();
    }
}
