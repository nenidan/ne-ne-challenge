package com.github.nenidan.ne_ne_challenge.domain.shop.global;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.dto.ProductResponse;

public interface ProductRestClient {
    ProductResponse getProduct(Long productId);
}
