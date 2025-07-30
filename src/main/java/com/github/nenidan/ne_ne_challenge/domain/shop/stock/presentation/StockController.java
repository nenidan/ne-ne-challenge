package com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.StockService;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto.AddStockCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation.dto.AddStockRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation.dto.AddStockResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation.mapper.AddStockMapper;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PatchMapping("/products/{productId}/stocks")
    public ResponseEntity<ApiResponse<AddStockResponse>> increaseStock(
        @PathVariable Long productId,
        @RequestBody @Valid AddStockRequest addStockRequest
    ) {
        AddStockCommand addStockCommand = AddStockMapper.toAddStockCommand(productId, addStockRequest);
        AddStockResponse addStockResponse = AddStockMapper.toAddStockResponse(
            stockService.increaseStock(addStockCommand)
        );
        return ApiResponse.success(HttpStatus.OK, "재고가 성공적으로 추가되었습니다.", addStockResponse);
    }

    @GetMapping("/products/{productId}/stocks")
    public ResponseEntity<ApiResponse<AddStockResponse>> getStock(
        @PathVariable Long productId
    ) {
        AddStockResponse addStockResponse = AddStockMapper.toAddStockResponse(stockService.getStock(productId));
        return ApiResponse.success(HttpStatus.OK, "재고가 성공적으로 조회되었습니다.", addStockResponse);
    }
}