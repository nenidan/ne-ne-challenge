package com.github.nenidan.ne_ne_challenge.domain.shop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.shop.dto.response.OrderResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.service.OrderService;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
        @RequestParam Long userId,
        @RequestParam Long productId
    ) {
        OrderResponse orderResponse = orderService.createOrder(userId, productId);
        return ApiResponse.success(HttpStatus.CREATED, "주문이 생성되었습니다.", orderResponse);
    }
}
