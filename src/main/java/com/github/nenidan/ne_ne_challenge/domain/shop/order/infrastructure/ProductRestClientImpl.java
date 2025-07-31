package com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopException;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.ProductRestClient;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.ProductResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;

import jakarta.annotation.PostConstruct;

@Component
public class ProductRestClientImpl implements ProductRestClient {

    @Value("${external.base-url}")
    private String baseUrl;
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
    public ProductResponse getProduct(Long productId) {

        ApiResponse<ProductResponse> apiResponse = restClient.get()
            .uri("/products/{id}", productId)
            .retrieve()
            .body(new ParameterizedTypeReference<>() {
            });

        if (apiResponse == null) {
            throw new ShopException(ShopErrorCode.PRODUCT_NOT_FOUND);
        }

        return apiResponse.getData();
    }
}
