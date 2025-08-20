package com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

    @Schema(description = "상품 이름", example = "GS25 5000 기프티콘")
    @NotBlank(message = "상품명을 등록해야 합니다.")
    private String productName;

    @Schema(description = "상품 설명", example = "GS25에서 5000원 내 상품과 교환 가능합니다.")
    @NotBlank(message = "상품 설명을 등록해야 합니다.")
    private String productDescription;

    @Schema(description = "상품 현재 가격", example = "5000")
    @NotNull(message = "상품 가격을 등록해야 합니다.")
    @Positive(message = "상품 가격은 음수 일 수 없습니다.")
    private Integer productPrice;
}