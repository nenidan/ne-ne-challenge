package com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopException;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.Product;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.ProductRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.entity.ProductEntity;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    public ProductRepositoryImpl(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = ProductMapper.toEntity(product);
        productJpaRepository.save(productEntity);
        return ProductMapper.toDomain(productEntity);
    }

    @Override
    public Product findById(ProductId productId) {
        ProductEntity productEntity = productJpaRepository.findById(productId.getProductId())
            .orElseThrow(() -> new ShopException(ShopErrorCode.PRODUCT_NOT_FOUND));
        return ProductMapper.toDomain(productEntity);
    }

    @Override
    public Product update(ProductId productId, Product product) {
        ProductEntity productEntity = productJpaRepository.findById(productId.getProductId())
            .orElseThrow(() -> new ShopException(ShopErrorCode.PRODUCT_NOT_FOUND));

        productEntity.setProductName(product.getProductName());
        productEntity.setProductDescription(product.getProductDescription());
        productEntity.setProductPrice(product.getProductPrice());

        return ProductMapper.toDomain(productEntity);
    }

    @Override
    public List<Product> findAllByCursor(Long cursor, int size, String keyword) {
        return productJpaRepository.findAllByKeywordAndCursor(cursor, keyword, size)
                    .stream()
                    .map(ProductMapper::toDomain)
                    .toList();
    }

    @Override
    public void delete(ProductId productId) {
        ProductEntity productEntity = productJpaRepository.findById(productId.getProductId())
            .orElseThrow(() -> new ShopException(ShopErrorCode.PRODUCT_NOT_FOUND));
        productEntity.delete();
    }
}
