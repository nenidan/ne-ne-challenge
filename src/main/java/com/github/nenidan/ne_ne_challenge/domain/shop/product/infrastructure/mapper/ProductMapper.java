package com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.mapper;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.model.Product;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.entity.ProductEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

public class ProductMapper {

    public static Product toDomain(ProductEntity productEntity) {
        return Product.create(
            new ProductId(productEntity.getId()),
            productEntity.getProductName(),
            productEntity.getProductDescription(),
            productEntity.getProductPrice()
        );
    }

    public static ProductEntity toEntity(Product product) {
        return new ProductEntity(
            product.getProductId() == null ? null : product.getProductId().getValue(),
            product.getProductName(),
            product.getProductDescription(),
            product.getProductPrice(),
            product.getDeletedAt() == null ? null : product.getDeletedAt()
        );
    }
}
