package com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.controller.internal;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.ProductFacade;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.ProductResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.dto.ProductResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.dto.ProductStatisticsResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.mapper.ProductPresentationMapper;

@RestController
@RequestMapping
public class InternalProductController {

    private final ProductFacade productFacade;

    public InternalProductController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @GetMapping("/internal/products/{id}")
    public ResponseEntity<ProductResponse> findProduct(
        @PathVariable Long id
    ) {
        ProductResult productResult = productFacade.findProduct(id);
        ProductResponse productResponse = ProductPresentationMapper.fromProductResult(productResult);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/internal/statistics/products")
    public  ResponseEntity<List<ProductStatisticsResponse>> getAllProducts() {
        List<ProductStatisticsResponse> allProducts = productFacade.getAllProducts()
            .stream()
            .map(ProductStatisticsResponse::from)
            .toList();

        return ResponseEntity.ok(allProducts);
    }
}
