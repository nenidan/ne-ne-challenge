package com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.model.Stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddStockResponse {

    @Schema(description = "재고 식별자", example = "1")
    private final Long stockId;
    @Schema(description = "상품 식별자", example = "1")
    private final Long productId;
    @Schema(description = "재고 수량", example = "20")
    private final int quantity;

    public static AddStockResponse from(Stock stock){
        return new AddStockResponse(
            stock.getStockId().getValue(),
            stock.getProductId().getValue(),
            stock.getQuantity()
        );
    }
}
