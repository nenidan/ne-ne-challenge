package com.github.nenidan.ne_ne_challenge.domain.shop.order.application;

import org.springframework.stereotype.Component;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.OrderResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.OrderedProduct;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.OrderId;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.UserId;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.ProductResponse;
import com.github.nenidan.ne_ne_challenge.domain.user.dto.response.UserResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

@Component
public class OrderFacade {

    private final OrderService orderService;
    private final ProductRestClient productRestClient;
    private final UserRestClient userRestClient;

    public OrderFacade(OrderService orderService,  ProductRestClient productRestClient, UserRestClient userRestClient) {
        this.orderService = orderService;
        this.productRestClient = productRestClient;
        this.userRestClient = userRestClient;
    }

    public OrderResponse createOrder (Long userId, Long productId) {
        UserResponse user = userRestClient.getUser(userId);
        ProductResponse product = productRestClient.getProduct(productId);

        OrderedProduct orderedProduct = new OrderedProduct(
            new ProductId(product.getId()),
            product.getName(),
            product.getDescription(),
            product.getPrice()
        );
        return orderService.createOrder(new UserId(user.getId()), orderedProduct);
    }

    public void cancelOrder (Long orderId) {
        orderService.cancelOrder(new OrderId(orderId));
    }

    public OrderResponse findOrder (Long orderId) {
        return orderService.findOrder(new OrderId(orderId));
    }

    public CursorResponse<OrderResponse, Long> findAllOrders (Long userId, Long cursor, int size, String keyword) {
        return orderService.findAllOrder(new UserId(userId), cursor, size, keyword);
    }
}
