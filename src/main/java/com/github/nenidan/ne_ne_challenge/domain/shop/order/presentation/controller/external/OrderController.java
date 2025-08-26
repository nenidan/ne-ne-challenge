package com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation.controller.external;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {

    private final OrderFacade orderFacade;

    @Operation(summary = "주문 생성", description = "특정 상품에 대한 주문을 생성합니다. 포인트 차감 및 재고 차감 성공 시 주문 생성")
    @Tag(name = "주문 관리")
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

    @Operation(summary = "주문 취소", description = "특정 주문을 취소합니다. 포인트 복구 및 재고 복구 성공 시 주문 취소")
    @Tag(name = "주문 관리")
    @Parameter(
        name = "id",
        description = "주문 식별자",
        example = "1",
        in = ParameterIn.PATH
    )
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(
        @AuthenticationPrincipal Auth auth,
        @PathVariable Long id
    ) {
        orderFacade.cancelOrder(auth.getId(), id);
        return ApiResponse.success(HttpStatus.OK, "주문이 취소되었습니다.", null);
    }


    @Operation(summary = "주문 단 건 조회", description = "특정 상품에 대해 주문을 조회합니다.")
    @Tag(name = "주문 관리")
    @Parameter(
        name = "id",
        description = "주문 식별자",
        example = "1",
        in = ParameterIn.PATH
    )
    @GetMapping("/orders/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> findOrder(
        @AuthenticationPrincipal Auth auth,
        @PathVariable Long id
    ) {
        OrderResult orderResult = orderFacade.findOrder(auth.getId(), id);
        OrderResponse orderResponse = OrderPresentationMapper.fromOrderResult(orderResult);
        return ApiResponse.success(HttpStatus.OK, "주문이 조회되었습니다.", orderResponse);
    }

    @Operation(summary = "주문 다 건 조회", description = "최신 주문 순으로 여러 주문을 조회합니다.")
    @Tag(name = "주문 관리")
    @Parameters({
        @Parameter(
            name = "cursor",
            description = "페이지 기준점",
            example = "1",
            in = ParameterIn.QUERY
        ),
        @Parameter(
            name = "size",
            description = "페이지 크기",
            example = "30",
            in = ParameterIn.QUERY,
            required = true
        ),
        @Parameter(
            name = "keyword",
            description = "검색어",
            example = "GS25",
            in = ParameterIn.QUERY
        ),
    })
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
