package com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.exception.ProductErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.exception.ProductException;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.model.Product;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.repository.ProductRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.entity.ProductEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.mapper.ProductMapper;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductQueryDslRepository  productQueryDslRepository;

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = ProductMapper.toEntity(product);
        productJpaRepository.save(productEntity);
        return ProductMapper.toDomain(productEntity);
    }

    @Override
    public Product findById(ProductId productId) {
        ProductEntity productEntity = productJpaRepository.findById(productId.getValue())
            .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));
        return ProductMapper.toDomain(productEntity);
    }

    @Override
    public List<Product> findAllByCursor(Long cursor, int limit, String keyword) {
        return productQueryDslRepository.findAllProductsBy(cursor, keyword, limit)
                    .stream()
                    .map(ProductMapper::toDomain)
                    .toList();
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll()
            .stream()
            .map(ProductMapper::toDomain)
            .toList();
    }
}
