package com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.controller.external;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.CreateProductCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.ProductResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.UpdateProductCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.service.ProductService;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.dto.CreateProductRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.dto.ProductResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.dto.UpdateProductRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.mapper.ProductPresentationMapper;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
        @RequestBody @Valid CreateProductRequest createProductRequest
    ) {
        CreateProductCommand createProductCommand = ProductPresentationMapper.toCreateProductCommand(
            createProductRequest);
        ProductResult productResult = productService.createProduct(createProductCommand);
        ProductResponse productResponse = ProductPresentationMapper.fromProductResult(productResult);
        return ApiResponse.success(HttpStatus.CREATED, "상품이 성공적으로 등록되었습니다.", productResponse);
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
        @PathVariable Long id,
        @RequestBody UpdateProductRequest updateProductRequest
    ) {
        UpdateProductCommand updateProductCommand = ProductPresentationMapper.toUpdateProductCommand(id,
            updateProductRequest);
        ProductResult productResult = productService.updateProduct(id, updateProductCommand);
        ProductResponse productResponse = ProductPresentationMapper.fromProductResult(productResult);
        return ApiResponse.success(HttpStatus.OK, "상품이 성공적으로 수정되었습니다.", productResponse);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> findProduct(
        @PathVariable Long id
    ) {
        ProductResult productResult = productService.findProduct(id);
        ProductResponse productResponse = ProductPresentationMapper.fromProductResult(productResult);
        return ApiResponse.success(HttpStatus.OK, "상품이 성공적으로 조회되었습니다.", productResponse);
    }

    @GetMapping("/products")
    public ResponseEntity<ApiResponse<CursorResponse<ProductResponse, Long>>> findAllProducts(
        @RequestParam(required = false) Long cursor,
        @RequestParam(defaultValue = "10") @Min(1) int size,
        @RequestParam(required = false) String keyword
    ) {
        CursorResponse<ProductResult, Long> products = productService.findAllProducts(cursor, size, keyword);
        CursorResponse<ProductResponse, Long> productResponseLongCursorResponse = ProductPresentationMapper.fromCursorProductResult(
            products);
        return ApiResponse.success(HttpStatus.OK, "상품이 성공적으로 조회되었습니다.", productResponseLongCursorResponse);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(
        @PathVariable Long id
    ) {
        productService.deleteProduct(id);
        return ApiResponse.success(HttpStatus.OK,"상품이 성공적으로 삭제되었습니다.", null);
    }
}
