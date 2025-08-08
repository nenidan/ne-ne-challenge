package com.github.nenidan.ne_ne_challenge.global.client.product;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.global.client.product.dto.ProductResponse;
import com.github.nenidan.ne_ne_challenge.global.client.product.dto.ProductStatisticsResponse;

public interface ProductRestClient {
    ProductResponse getProduct(ProductId productId);
    List<ProductStatisticsResponse> getAllProducts();
}
