package com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class AddStockCommand {

    private final int quantity;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AddStockCommand(
            @JsonProperty("quantity") int quantity
    ){
        this.quantity = quantity;
    }

    public static AddStockCommand from(int quantity) {
        return new AddStockCommand(quantity);
    }
}
