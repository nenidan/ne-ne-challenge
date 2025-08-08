package com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation.controller.internal;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.OrderFacade;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation.dto.OrderStatisticsResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class InternalOrderController {

    private final OrderFacade orderFacade;

    @GetMapping("/internal/statistics/orders")
    public ResponseEntity<List<OrderStatisticsResponse>> getAllOrders() {
        List<OrderStatisticsResponse> allOrders = orderFacade.findAllOrders()
            .stream()
            .map(OrderStatisticsResponse::from)
            .toList();

        return ResponseEntity.ok(allOrders);
    }
}

