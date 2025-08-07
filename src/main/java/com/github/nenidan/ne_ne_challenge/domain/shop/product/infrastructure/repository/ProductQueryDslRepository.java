package com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.repository;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.entity.ProductEntity;

public interface ProductQueryDslRepository {
    List<ProductEntity> findAllProductsBy(Long cursor, String keyword, int limit);
}
