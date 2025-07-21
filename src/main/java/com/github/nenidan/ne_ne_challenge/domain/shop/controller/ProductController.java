package com.github.nenidan.ne_ne_challenge.domain.shop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.shop.dto.response.ProductResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.dto.request.CreateProductRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.service.ProductService;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
        @RequestBody CreateProductRequest createProductRequest
    ) {
        ProductResponse productResponse = productService.createProduct(createProductRequest);
        return ApiResponse.success(HttpStatus.CREATED, "상품이 성공적으로 등록되었습니다.", productResponse);
    }
}
