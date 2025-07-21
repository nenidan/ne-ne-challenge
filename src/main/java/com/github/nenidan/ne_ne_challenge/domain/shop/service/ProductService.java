package com.github.nenidan.ne_ne_challenge.domain.shop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.shop.dto.request.CreateProductRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.dto.request.UpdateProductRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.dto.response.ProductResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.entity.Product;
import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopException;
import com.github.nenidan.ne_ne_challenge.domain.shop.repository.ProductRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.util.ProductMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductResponse createProduct(CreateProductRequest createProductRequest) {
        Product product = createProductRequest.toEntity();

        Product savedProduct = productRepository.save(product);

        return ProductResponse.fromEntity(savedProduct);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, UpdateProductRequest updateProductRequest) {
        Product product = productRepository.findById(id).orElseThrow(
            () -> new ShopException(ShopErrorCode.PRODUCT_NOT_FOUND)
        );

        productMapper.updateProductFromDto(updateProductRequest, product);

        return ProductResponse.fromEntity(product);
    }
}

