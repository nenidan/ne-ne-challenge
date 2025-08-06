package com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.repository;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.model.Product;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

public interface ProductRepository {
    Product save(Product product);
    Product findById(ProductId productId);
    List<Product> findAllByCursor(Long cursor, int size, String keyword);
    List<Product> findAll();
}
