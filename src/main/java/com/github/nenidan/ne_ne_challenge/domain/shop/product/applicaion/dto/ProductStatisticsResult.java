package com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.model.Product;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class ProductStatisticsResult {

    private final ProductId id;
    private final String name;
    private final String description;
    private final Integer price;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProductStatisticsResult(
            @JsonProperty("id") ProductId id,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("price") Integer price
    ){
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public static ProductStatisticsResult fromEntity(Product product) {
        return new ProductStatisticsResult(
            product.getProductId(),
            product.getProductName(),
            product.getProductDescription(),
            product.getProductPrice()
        );
    }
}
