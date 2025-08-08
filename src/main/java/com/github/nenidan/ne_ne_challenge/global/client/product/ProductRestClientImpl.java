package com.github.nenidan.ne_ne_challenge.global.client.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.global.client.product.dto.ProductResponse;
import com.github.nenidan.ne_ne_challenge.global.client.product.dto.ProductStatisticsResponse;

import jakarta.annotation.PostConstruct;

@Component
public class ProductRestClientImpl implements ProductRestClient {

    @Value("${external.base-url}")
    private String baseUrl;

    private static final String PRODUCT_ENDPOINT = "/internal/products/{id}";
    private static final String ALL_PRODUCTS_ENDPOINT = "/internal/statistics/products";

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
    public ProductResponse getProduct(ProductId productId) {
        return restClient.get()
            .uri(PRODUCT_ENDPOINT, productId.getValue())
            .retrieve()
            .body(ProductResponse.class);
    }

    @Override
    public List<ProductStatisticsResponse> getAllProducts() {
        return restClient.get()
            .uri(ALL_PRODUCTS_ENDPOINT)
            .retrieve()
            .body(new ParameterizedTypeReference<>() {
            });
    }
}
