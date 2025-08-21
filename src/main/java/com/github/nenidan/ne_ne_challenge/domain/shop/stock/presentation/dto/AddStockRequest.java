package com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddStockRequest {

    @Schema(description = "재고 수량", example = "20")
    @NotNull(message = "수량에 빈값이 들어올 수 없습니다.")
    private Integer quantity;
}
