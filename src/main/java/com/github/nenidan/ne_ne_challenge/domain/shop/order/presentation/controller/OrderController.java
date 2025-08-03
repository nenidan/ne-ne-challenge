package com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.OrderFacade;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.CreateOrderCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.FindCursorOrderCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.OrderResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation.dto.CreateOrderRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation.dto.OrderResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation.mapper.OrderPresentationMapper;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderFacade orderFacade;

    @PostMapping("/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
        @AuthenticationPrincipal Auth auth,
        @RequestBody CreateOrderRequest createOrderRequest
    ) {
        CreateOrderCommand createOrderCommand = OrderPresentationMapper.toCreateOrderCommand(auth.getId(), createOrderRequest);
        OrderResult orderResult = orderFacade.createOrder(createOrderCommand);
        OrderResponse orderResponse = OrderPresentationMapper.fromOrderResult(orderResult);
        return ApiResponse.success(HttpStatus.CREATED, "주문이 생성되었습니다.", orderResponse);
    }

    @PatchMapping("/orders/{id}")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(
        @AuthenticationPrincipal Auth auth,
        @PathVariable Long id
    ) {
        orderFacade.cancelOrder(auth.getId(), id);
        return ApiResponse.success(HttpStatus.OK, "주문이 취소되었습니다.", null);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> findOrder(
        @AuthenticationPrincipal Auth auth,
        @PathVariable Long id
    ) {
        OrderResult orderResult = orderFacade.findOrder(auth.getId(), id);
        OrderResponse orderResponse = OrderPresentationMapper.fromOrderResult(orderResult);
        return ApiResponse.success(HttpStatus.OK, "주문이 조회되었습니다.", orderResponse);
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<CursorResponse<OrderResponse, Long>>> findOrder(
        @AuthenticationPrincipal Auth auth,
        @RequestParam(required = false) Long cursor,
        @RequestParam(defaultValue = "10") @Min(1) int size,
        @RequestParam(required = false) String keyword
    ) {
        FindCursorOrderCommand findCursorOrderCommand = OrderPresentationMapper.toFindCursorOrderCommand(auth.getId(),
            cursor, size, keyword);
        CursorResponse<OrderResult, Long> orders = orderFacade.findAllOrders(findCursorOrderCommand);
        CursorResponse<OrderResponse, Long> orderResponseLongCursorResponse = OrderPresentationMapper.fromCursorOrderResult(
            orders);
        return ApiResponse.success(HttpStatus.OK, "주문이 조회되었습니다.", orderResponseLongCursorResponse);
    }
}
