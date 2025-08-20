package com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderDetailDto {

    @Schema(description = "상품 식별자", example = "1")
    private final Long productId;
    @Schema(description = "상품 이름", example = "GS25 5000 기프티콘")
    private final String productName;
    @Schema(description = "상품 설명", example = "GS25에서 5000원 내 상품과 교환 가능합니다.")
    private final String productDescription;
    @Schema(description = "상품 당시 가격", example = "5000")
    private final Integer priceAtOrder;
    @Schema(description = "상품 수량", example = "2")
    private final int quantity;
}
