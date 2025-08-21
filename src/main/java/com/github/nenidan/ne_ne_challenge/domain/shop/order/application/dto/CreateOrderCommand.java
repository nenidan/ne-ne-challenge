package com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderCommand {

    private UserId userId;
    private ProductId productId;
    private int quantity;

}
