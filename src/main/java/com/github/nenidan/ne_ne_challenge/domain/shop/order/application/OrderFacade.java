package com.github.nenidan.ne_ne_challenge.domain.shop.order.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.CreateOrderCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.FindCursorOrderCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.OrderResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.OrderStatisticsResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.service.OrderService;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.OrderDetail;
import com.github.nenidan.ne_ne_challenge.global.client.stock.StockRestClient;
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
    private final ProductRestClient productRestClient;
    private final UserClient userClient;
    private final PointClient pointClient;
    private final StockRestClient stockRestClient;

    public OrderResult createOrder (CreateOrderCommand createOrderRequest) {
        boolean stockDecrease = false;
        boolean pointDecrease = false;

        // 1. 유저 검증 및 유저 정보 호출
        UserResponse user = userClient.getUserById(createOrderRequest.getUserId().getValue());
        // 2. 상품 검증 및 상품 정보 호출
        ProductResponse product = productRestClient.getProduct(createOrderRequest.getProductId());

        try {
            // 3. 예비 재고 감소
            stockRestClient.decreaseReservedStock(
                new ProductId(product.getId()),
                createOrderRequest.getQuantity()
            );
            stockDecrease = true;

            // 4. 포인트 결제 호출
            pointClient.decreasePoint(
                user.getId(),
                createOrderRequest.getQuantity() * product.getPrice(),
                "PRODUCT_PURCHASE"
            );
            pointDecrease = true;


            OrderDetail orderDetail = new OrderDetail(
                null,
                new ProductId(product.getId()),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                createOrderRequest.getQuantity()
            );

            // 5. 주문 생성
            OrderResult order = orderService.createOrder(new UserId(user.getId()), orderDetail);

            // 6. 주문 확정
            stockRestClient.decreaseStock(
                new ProductId(product.getId()),
                createOrderRequest.getQuantity()
            );
            return order;

        } catch (RuntimeException e) {
            // 감소된 재고 -> 복구
            if (stockDecrease) {
                stockRestClient.restoreStock(
                    createOrderRequest.getProductId(),
                    createOrderRequest.getQuantity()
                );
            }

            // 결제된 포인트 -> 복구
            if (pointDecrease) {
                // 포인트 결제 호출
                pointClient.increasePoint(
                    user.getId(),
                    createOrderRequest.getQuantity() * product.getPrice(),
                    "PRODUCT_REFUND"
                );
            }

            throw e;
        }
    }

    public void cancelOrder (Long userId, Long orderId) {
        boolean stockRestore = false;
        boolean pointRestore = false;

        // 1. 주문 조회
        OrderResult order = orderService.findOrder(new UserId(userId), new OrderId(orderId));

        try {
            // 2. 예비 재고 복구
            stockRestClient.restoreReservedStock(
                new ProductId(order.getOrderDetail().getProductId()),
                order.getOrderDetail().getQuantity()
            );
            stockRestore = true;

            // 3. 포인트 복구
            pointClient.increasePoint(
                userId,
                order.getOrderDetail().getQuantity() * order.getOrderDetail().getPriceAtOrder(),
                "PRODUCT_REFUND"
            );
            pointRestore = true;

            // 4. 주문 취소
            orderService.cancelOrder(new UserId(userId), new OrderId(orderId));

            // 5. 재고 복구 확정
            stockRestClient.restoreStock(
                new ProductId(order.getOrderDetail().getProductId()),
                order.getOrderDetail().getQuantity()
            );
        } catch (Exception e) {
            // 재고 복구 -> 재고 복구 취소
            if (stockRestore) {
                stockRestClient.decreaseStock(
                    new ProductId(order.getOrderDetail().getProductId()),
                    order.getOrderDetail().getQuantity()
                );
            }

            if (pointRestore) {
                // 3. 포인트 복구
                pointClient.decreasePoint(
                    userId,
                    order.getOrderDetail().getQuantity() * order.getOrderDetail().getPriceAtOrder(),
                    "PRODUCT_PURCHASE"
                );
            }

            throw e;
        }
    }

    public OrderResult findOrder (Long userId, Long orderId) {
        return orderService.findOrder(new UserId(userId), new OrderId(orderId));
    }

    public CursorResponse<OrderResult, Long> findAllOrders (FindCursorOrderCommand findCursorOrderCommand) {

        // 유저 검증
        userClient.getUserById(findCursorOrderCommand.getUserId().getValue());

        return orderService.findAllOrders(
            findCursorOrderCommand.getUserId(),
            findCursorOrderCommand.getCursor(),
            findCursorOrderCommand.getSize(),
            findCursorOrderCommand.getKeyword()
        );
    }

    public List<OrderStatisticsResult> findAllOrders() {
        return orderService.findAllOrders();
    }
}
