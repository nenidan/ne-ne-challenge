package com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopException;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.StockService;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto.StockResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PatchMapping("/stocks")
    public ResponseEntity<ApiResponse<StockResponse>> addStock(
        @RequestParam Long productId,
        @RequestParam int quantity,
        @RequestParam String action
    ) {
        if(action.equalsIgnoreCase("increase")){
            return ApiResponse.success(HttpStatus.OK, "재고가 성공적으로 추가되었습니다.", stockService.increaseStock(productId, quantity));
        } else if(action.equalsIgnoreCase("decrease")) {
            return ApiResponse.success(HttpStatus.OK, "재고가 성공적으로 차감되었습니다.", stockService.decreaseStock(productId, quantity));
        } else {
            throw new ShopException(ShopErrorCode.INVALID_ACTION);
        }
    }
}