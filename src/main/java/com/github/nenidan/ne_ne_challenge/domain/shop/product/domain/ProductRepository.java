package com.github.nenidan.ne_ne_challenge.domain.shop.product.domain;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.vo.ProductId;

public interface ProductRepository {
    Product save(Product product);
    Product findById(ProductId productId);
    Product update(ProductId productId, Product product);
    List<Product> findAllByCursor(Long cursor, int size, String keyword);
    void delete(ProductId productId);
}
