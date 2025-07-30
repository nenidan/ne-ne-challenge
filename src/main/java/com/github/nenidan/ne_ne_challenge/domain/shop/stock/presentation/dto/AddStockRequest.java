package com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddStockRequest {

    @NotBlank(message = "수량에 빈값이 들어올 수 없습니다.")
    private final int quantity;
}
