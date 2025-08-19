package com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddStockCommand {

    private final int quantity;

    public static AddStockCommand from(int quantity) {
        return new AddStockCommand(quantity);
    }
}
