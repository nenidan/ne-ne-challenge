package com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation.controller.external;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.dto.AddStockCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.application.service.StockService;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation.dto.AddStockRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation.dto.AddStockResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.presentation.mapper.StockPresentationMapper;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @Operation(summary = "재고 추가", description = "특정 상품에 대해 재고를 추가합니다.")
    @Tag(name = "재고 관리")
    @Parameter(
        name = "productId",
        description = "상품 식별자",
        example = "1",
        in = ParameterIn.PATH
    )
    @PatchMapping("/products/{productId}/stocks")
    public ResponseEntity<ApiResponse<AddStockResponse>> increaseStock(
        @PathVariable Long productId,
        @RequestBody @Valid AddStockRequest addStockRequest
    ) {
        AddStockCommand addStockCommand = AddStockCommand.from(addStockRequest.getQuantity());
        AddStockResponse addStockResponse = StockPresentationMapper.toAddStockResponse(
            stockService.inBoundStock(new ProductId(productId), addStockCommand)
        );
        return ApiResponse.success(HttpStatus.OK, "재고가 성공적으로 추가되었습니다.", addStockResponse);
    }

    @Operation(summary = "재고 조회", description = "특정 상품에 대한 재고를 조회합니다.")
    @Tag(name = "재고 관리")
    @Parameter(
        name = "productId",
        description = "상품 식별자",
        example = "1",
        in = ParameterIn.PATH
    )
    @GetMapping("/products/{productId}/stocks")
    public ResponseEntity<ApiResponse<AddStockResponse>> getStock(
        @PathVariable Long productId
    ) {
        AddStockResponse addStockResponse = StockPresentationMapper.toAddStockResponse(stockService.getStock(productId));
        return ApiResponse.success(HttpStatus.OK, "재고가 성공적으로 조회되었습니다.", addStockResponse);
    }
}