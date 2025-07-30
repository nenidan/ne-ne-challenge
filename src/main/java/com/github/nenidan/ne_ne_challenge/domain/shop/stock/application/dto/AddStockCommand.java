package com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddStockCommand {

    private final ProductId productId;
    private final int quantity;
}
