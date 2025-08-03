package com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.event;

import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StockRestoreEvent {
    private final ProductId productId;
    private final Integer quantity;
}
