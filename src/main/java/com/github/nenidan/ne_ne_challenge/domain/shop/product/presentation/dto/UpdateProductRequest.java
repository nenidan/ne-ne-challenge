package com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {

    @Schema(description = "수정할 상품 이름", example = "GS25 6000 기프티콘")
    private String productName;
    @Schema(description = "수정할 상품 설명", example = "GS25에서 6000원 내 상품과 교환 가능합니다.")
    private String productDescription;
    @Schema(description = "수정할 상품 가격", example = "6000")
    private Integer productPrice;
}

