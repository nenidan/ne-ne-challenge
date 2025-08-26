package com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.controller.external;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.ProductFacade;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.CreateProductCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.ProductResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.UpdateProductCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.dto.CreateProductRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.dto.ProductResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.dto.UpdateProductRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.mapper.ProductPresentationMapper;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductFacade productFacade;

    public ProductController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @Operation(summary = "상품 생성", description = "상품을 생성합니다.")
    @Tag(name = "상품 관리")
    @PostMapping("/products")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
        @RequestBody @Valid CreateProductRequest createProductRequest
    ) {
        CreateProductCommand createProductCommand = ProductPresentationMapper.toCreateProductCommand(
            createProductRequest);
        ProductResult productResult = productFacade.createProduct(createProductCommand);
        ProductResponse productResponse = ProductPresentationMapper.fromProductResult(productResult);
        return ApiResponse.success(HttpStatus.CREATED, "상품이 성공적으로 등록되었습니다.", productResponse);
    }

    @Operation(summary = "상품 정보 수정", description = "특정 상품 정보를 수정합니다.")
    @Tag(name = "상품 관리")
    @Parameter(
        name = "id",
        description = "상품 식별자",
        example = "1",
        in = ParameterIn.PATH
    )
    @PatchMapping("/products/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
        @PathVariable Long id,
        @RequestBody UpdateProductRequest updateProductRequest
    ) {
        UpdateProductCommand updateProductCommand = ProductPresentationMapper.toUpdateProductCommand(id,
            updateProductRequest);
        ProductResult productResult = productFacade.updateProduct(id, updateProductCommand);
        ProductResponse productResponse = ProductPresentationMapper.fromProductResult(productResult);
        return ApiResponse.success(HttpStatus.OK, "상품이 성공적으로 수정되었습니다.", productResponse);
    }

    @Operation(summary = "상품 단 건 조회", description = "특정 상품을 조회합니다.")
    @Tag(name = "상품 관리")
    @Parameter(
        name = "id",
        description = "상품 식별자",
        example = "1",
        in = ParameterIn.PATH
    )
    @GetMapping("/products/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> findProduct(
        @PathVariable Long id
    ) {
        ProductResult productResult = productFacade.findProduct(id);
        ProductResponse productResponse = ProductPresentationMapper.fromProductResult(productResult);
        return ApiResponse.success(HttpStatus.OK, "상품이 성공적으로 조회되었습니다.", productResponse);
    }

    @Operation(summary = "상품 다 건 조회", description = "최신 순으로 여러 상품을 조회합니다. (검색 기능 포함)")
    @Tag(name = "상품 관리")
    @Parameters({
        @Parameter(
            name = "cursor",
            description = "페이지 기준점",
            in = ParameterIn.QUERY
        ),
        @Parameter(
            name = "size",
            description = "페이지 크기",
            in = ParameterIn.QUERY
        ),
        @Parameter(
            name = "keyword",
            description = "검색어",
            in = ParameterIn.QUERY
        ),
    })
    @GetMapping("/products")
    public ResponseEntity<ApiResponse<CursorResponse<ProductResponse, Long>>> findAllProducts(
        @RequestParam(required = false) List<Long> cursor,
        @RequestParam(defaultValue = "10") @Min(1) int size,
        @RequestParam(required = false) String keyword
    ) {
        CursorResponse<ProductResult, Long> products = productFacade.findAllProducts(cursor, size, keyword);
        CursorResponse<ProductResponse, Long> productResponseLongCursorResponse = ProductPresentationMapper.fromCursorProductResult(
            products);
        return ApiResponse.success(HttpStatus.OK, "상품이 성공적으로 조회되었습니다.", productResponseLongCursorResponse);
    }

    @Operation(summary = "상품 삭제", description = "특정 상품을 삭제합니다.")
    @Tag(name = "상품 관리")
    @Parameter(
        name = "id",
        description = "상품 식별자",
        example = "1",
        in = ParameterIn.PATH
    )
    @DeleteMapping("/products/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(
        @PathVariable Long id
    ) {
        productFacade.deleteProduct(id);
        return ApiResponse.success(HttpStatus.OK,"상품이 성공적으로 삭제되었습니다.", null);
    }
}
