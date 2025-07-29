package com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.StockService;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto.StockRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto.StockResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PatchMapping("/stocks")
    public ResponseEntity<ApiResponse<StockResponse>> increaseOrDecreaseStock(
        @RequestParam Long productId,
        @RequestBody StockRequest stockRequest
    ) {
        return ApiResponse.success(HttpStatus.OK, "재고가 성공적으로 추가되었습니다.", stockService.increaseStock(productId, stockRequest.getQuantity()));
    }

    @GetMapping("/stocks")
    public ResponseEntity<ApiResponse<StockResponse>> getStock(
        @RequestParam Long productId
    ) {
        return ApiResponse.success(HttpStatus.OK, "재고가 성공적으로 조회되었습니다.", stockService.getStock(productId));
    }
}