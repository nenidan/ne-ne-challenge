package com.github.nenidan.ne_ne_challenge.global.client.product;

import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.global.client.product.dto.ProductResponse;

public interface ProductRestClient {
    ProductResponse getProduct(ProductId productId);
}
