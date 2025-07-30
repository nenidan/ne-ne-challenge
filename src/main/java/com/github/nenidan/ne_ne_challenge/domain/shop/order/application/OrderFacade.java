package com.github.nenidan.ne_ne_challenge.domain.shop.order.application;

import org.springframework.stereotype.Component;

import com.github.nenidan.ne_ne_challenge.domain.shop.global.ProductRestClient;
import com.github.nenidan.ne_ne_challenge.domain.shop.global.UserRestClient;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.CreateOrderCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.FindCursorOrderCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.OrderResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.OrderDetail;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.dto.ProductResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.OrderId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.user.dto.response.UserResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

@Component
public class OrderFacade {

    private final OrderService orderService;
    private final ProductRestClient productRestClient;
    private final UserRestClient userRestClient;

    public OrderFacade(OrderService orderService, ProductRestClient productRestClient, UserRestClient userRestClient) {
        this.orderService = orderService;
        this.productRestClient = productRestClient;
        this.userRestClient = userRestClient;
    }

    public OrderResult createOrder (CreateOrderCommand createOrderRequest) {
        UserResponse user = userRestClient.getUser(createOrderRequest.getUserId().getValue());
        ProductResponse product = productRestClient.getProduct(createOrderRequest.getProductId().getValue());

        OrderDetail orderDetail = new OrderDetail(
            new ProductId(product.getId()),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            createOrderRequest.getQuantity()
        );
        return orderService.createOrder(new UserId(user.getId()), orderDetail);
    }

    public void cancelOrder (Long orderId) {
        orderService.cancelOrder(new OrderId(orderId));
    }

    public OrderResult findOrder (Long orderId) {
        return orderService.findOrder(new OrderId(orderId));
    }

    public CursorResponse<OrderResult, Long> findAllOrders (FindCursorOrderCommand findCursorOrderCommand) {
        return orderService.findAllOrder(
            findCursorOrderCommand.getUserId(),
            findCursorOrderCommand.getCursor(),
            findCursorOrderCommand.getSize(),
            findCursorOrderCommand.getKeyword()
        );
    }
}
