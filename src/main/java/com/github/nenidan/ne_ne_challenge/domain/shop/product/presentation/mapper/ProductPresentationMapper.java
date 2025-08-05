package com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.mapper;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.CreateProductCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.ProductResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.UpdateProductCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.dto.CreateProductRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.dto.ProductResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.dto.UpdateProductRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

public class ProductPresentationMapper {

    public static CreateProductCommand  toCreateProductCommand(CreateProductRequest createProductRequest){
        return new CreateProductCommand(
            createProductRequest.getProductName(),
            createProductRequest.getProductDescription(),
            createProductRequest.getProductPrice()
        );
    }

    public static UpdateProductCommand toUpdateProductCommand(Long productId, UpdateProductRequest updateProductRequest){
        return new UpdateProductCommand(
            new ProductId(productId),
            updateProductRequest.getProductName(),
            updateProductRequest.getProductDescription(),
            updateProductRequest.getProductPrice()
        );
    }

    public static ProductResponse fromProductResult (ProductResult productResult) {
        return new ProductResponse(
            productResult.getId().getValue(),
            productResult.getName(),
            productResult.getDescription(),
            productResult.getPrice()
        );
    }

    public static CursorResponse<ProductResponse, Long> fromCursorProductResult(
        CursorResponse<ProductResult, Long> cursorResponse) {

        List<ProductResponse> mappedContents = cursorResponse.getContent().stream()
            .map(ProductResponse::from)
            .toList();

        return CursorResponse.of(
            mappedContents,
            cursorResponse.getNextCursor(),
            cursorResponse.isHasNext()
        );
    }

}
