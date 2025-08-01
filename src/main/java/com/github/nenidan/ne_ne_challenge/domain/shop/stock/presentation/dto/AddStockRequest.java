package com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddStockRequest {

    @NotNull(message = "수량에 빈값이 들어올 수 없습니다.")
    private final Integer quantity;
}
