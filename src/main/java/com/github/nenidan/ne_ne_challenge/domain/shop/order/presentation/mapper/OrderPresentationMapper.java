package com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation.mapper;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.CreateOrderCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.FindCursorOrderCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.OrderResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation.dto.CreateOrderRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation.dto.OrderDetailDto;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.presentation.dto.OrderResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

public class OrderPresentationMapper {

    public static CreateOrderCommand toCreateOrderCommand(Long userId, CreateOrderRequest createOrderRequest){
        return new CreateOrderCommand(
            new UserId(userId),
            new ProductId(createOrderRequest.getProductId()),
            createOrderRequest.getQuantity()
        );
    }

    public static FindCursorOrderCommand toFindCursorOrderCommand(Long userId, Long cursor, int size, String keyword){
        return new FindCursorOrderCommand(
            new UserId(userId),
            cursor,
            size,
            keyword
        );
    }

    public static OrderResponse fromOrderResult(OrderResult orderResult){
        return new OrderResponse(
            orderResult.getOrderId(),
            orderResult.getUserId(),
            new OrderDetailDto(
                orderResult.getOrderDetail().getProductId(),
                orderResult.getOrderDetail().getProductName(),
                orderResult.getOrderDetail().getProductDescription(),
                orderResult.getOrderDetail().getPriceAtOrder(),
                orderResult.getOrderDetail().getQuantity()
            ),
            orderResult.getStatus()
        );
    }

    public static CursorResponse<OrderResponse, Long> fromCursorOrderResult(CursorResponse<OrderResult, Long> cursorOrderResult){
        List<OrderResponse> orderResponses = cursorOrderResult.getContent().stream()
            .map(OrderPresentationMapper::fromOrderResult)
            .toList();

        return new CursorResponse<>(
            orderResponses,
            cursorOrderResult.getNextCursor(),
            cursorOrderResult.isHasNext()
        );
    }
}
