package com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.Product;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.entity.ProductEntity;

public class ProductMapper {

    public static Product toDomain(ProductEntity productEntity) {
        return new Product(
            new ProductId(productEntity.getId()),
            productEntity.getProductName(),
            productEntity.getProductDescription(),
            productEntity.getProductPrice()
        );
    }

    public static ProductEntity toEntity(Product product) {
        return new ProductEntity(
            product.getProductName(),
            product.getProductDescription(),
            product.getProductPrice()
        );
    }
}
