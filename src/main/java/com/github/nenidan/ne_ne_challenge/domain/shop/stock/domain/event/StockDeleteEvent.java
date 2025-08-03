package com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.event;

import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StockDeleteEvent {

    private final ProductId productId;
}
