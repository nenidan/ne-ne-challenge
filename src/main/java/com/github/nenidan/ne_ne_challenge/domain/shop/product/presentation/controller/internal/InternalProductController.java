package com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.controller.internal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.ProductResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.service.ProductService;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.dto.ProductResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.mapper.ProductPresentationMapper;

@RestController
@RequestMapping
public class InternalProductController {

    private final ProductService productService;

    public InternalProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/internal/products/{id}")
    public ResponseEntity<ProductResponse> findProduct(
        @PathVariable Long id
    ) {
        ProductResult productResult = productService.findProduct(id);
        ProductResponse productResponse = ProductPresentationMapper.fromProductResult(productResult);
        return ResponseEntity.ok(productResponse);
    }
}
