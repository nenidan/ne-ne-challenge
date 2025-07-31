package com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.OrderFacade;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.OrderResponse;
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
        @RequestParam Long productId
    ) {
        OrderResponse orderResponse = orderFacade.createOrder(auth.getId(), productId);
        return ApiResponse.success(HttpStatus.CREATED, "주문이 생성되었습니다.", orderResponse);
    }

    @PatchMapping("/orders/{id}")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(
        @PathVariable Long id
    ) {
        orderFacade.cancelOrder(id);
        return ApiResponse.success(HttpStatus.OK, "주문이 취소되었습니다.", null);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> findOrder(
        @PathVariable Long id
    ) {
        OrderResponse orderResponse = orderFacade.findOrder(id);
        return ApiResponse.success(HttpStatus.OK, "주문이 조회되었습니다.", orderResponse);
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<CursorResponse<OrderResponse, Long>>> findOrder(
        @RequestParam Long userId,
        @RequestParam(required = false) Long cursor,
        @RequestParam(defaultValue = "10") @Min(1) int size,
        @RequestParam(required = false) String keyword
    ) {
        CursorResponse<OrderResponse, Long> allOrder = orderFacade.findAllOrders(userId, cursor, size, keyword);
        return ApiResponse.success(HttpStatus.OK, "주문이 조회되었습니다.", allOrder);
    }
}
