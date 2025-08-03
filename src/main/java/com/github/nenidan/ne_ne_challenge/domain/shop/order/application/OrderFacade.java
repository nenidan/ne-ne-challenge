package com.github.nenidan.ne_ne_challenge.domain.shop.order.application;

import org.springframework.stereotype.Component;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.CreateOrderCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.FindCursorOrderCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.OrderResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.service.OrderCompensationService;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.service.OrderService;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.exception.OrderErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.exception.OrderException;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.OrderDetail;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.OrderId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;
import com.github.nenidan.ne_ne_challenge.global.client.point.PointClient;
import com.github.nenidan.ne_ne_challenge.global.client.product.ProductRestClient;
import com.github.nenidan.ne_ne_challenge.global.client.product.dto.ProductResponse;
import com.github.nenidan.ne_ne_challenge.global.client.user.UserClient;
import com.github.nenidan.ne_ne_challenge.global.client.user.dto.UserResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;
    private final OrderCompensationService orderCompensationService;
    private final ProductRestClient productRestClient;
    private final UserClient userClient;
    private final PointClient pointClient;

    public OrderResult createOrder (CreateOrderCommand createOrderRequest) {
        // 유저 검증 및 유저 정보 호출
        UserResponse user = userClient.getUserById(createOrderRequest.getUserId().getValue());
        // 상품 검증 및 상품 정보 호출
        ProductResponse product = productRestClient.getProduct(createOrderRequest.getProductId());
        // 포인트 결제 호출
        pointClient.decreasePoint(
            user.getId(),
            createOrderRequest.getQuantity() * product.getPrice(),
            "PRODUCT_PURCHASE"
        );

        OrderDetail orderDetail = new OrderDetail(
            null,
            new ProductId(product.getId()),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            createOrderRequest.getQuantity()
        );
        return orderService.createOrder(new UserId(user.getId()), orderDetail);
    }

    public void cancelOrder (Long userId, Long orderId) {
        OrderResult orderResult = orderService.cancelOrder(new UserId(userId), new OrderId(orderId));
        try {
            pointClient.increasePoint(
                userId,
                orderResult.getOrderDetail().getQuantity() * orderResult.getOrderDetail().getPriceAtOrder(),
                "PRODUCT_REFUND"
            );
        } catch (Exception e) {
            // 실패하면 보상 트랜잭션 실행
            orderCompensationService.compensateOrderCancel(new UserId(userId), new OrderId(orderId));
            throw new OrderException(OrderErrorCode.ORDER_FAILED_CANCEL);
        }
    }

    public OrderResult findOrder (Long userId, Long orderId) {
        return orderService.findOrder(new UserId(userId), new OrderId(orderId));
    }

    public CursorResponse<OrderResult, Long> findAllOrders (FindCursorOrderCommand findCursorOrderCommand) {

        // 유저 검증
        userClient.getUserById(findCursorOrderCommand.getUserId().getValue());

        return orderService.findAllOrder(
            findCursorOrderCommand.getUserId(),
            findCursorOrderCommand.getCursor(),
            findCursorOrderCommand.getSize(),
            findCursorOrderCommand.getKeyword()
        );
    }
}
