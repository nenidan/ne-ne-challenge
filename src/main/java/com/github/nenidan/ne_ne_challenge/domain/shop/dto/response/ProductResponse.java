package com.github.nenidan.ne_ne_challenge.domain.shop.dto.response;

import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.shop.entity.Product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final Integer price;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;

    public static ProductResponse fromEntity(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getProductName(),
            product.getProductDescription(),
            product.getProductPrice(),
            product.getCreatedAt(),
            product.getUpdatedAt(),
            product.getDeletedAt()
        );
    }
}
