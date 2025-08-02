package com.github.nenidan.ne_ne_challenge.domain.shop.order.application;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.ProductResponse;

public interface ProductRestClient {
    ProductResponse getProduct(Long productId);
}
