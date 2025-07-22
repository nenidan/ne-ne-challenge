package com.github.nenidan.ne_ne_challenge.domain.shop.service;

import java.util.List;

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
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

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

    public ProductResponse findProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
            () -> new ShopException(ShopErrorCode.PRODUCT_NOT_FOUND)
        );

        return ProductResponse.fromEntity(product);
    }

    public CursorResponse<ProductResponse, Long> findAllProducts(
        Long cursor,
        int size,
        String keyword
    ) {
        List<ProductResponse> productList = productRepository.findByKeyword(cursor,keyword, size+1)
            .stream()
            .map(ProductResponse::fromEntity)
            .toList();

        boolean hasNext = productList.size() > size;

        List<ProductResponse> content = hasNext ? productList.subList(0, size) : productList;

        Long nextCursor = hasNext ? productList.get(productList.size() - 1).getId() : null;

        return new CursorResponse<>(content, nextCursor, productList.size() > size);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
            () -> new ShopException(ShopErrorCode.PRODUCT_NOT_FOUND)
        );

        product.delete();
    }
}

