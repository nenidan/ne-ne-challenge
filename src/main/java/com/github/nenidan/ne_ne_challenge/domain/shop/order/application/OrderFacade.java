package com.github.nenidan.ne_ne_challenge.domain.shop.order.application;

import org.springframework.stereotype.Component;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.OrderResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.OrderedProduct;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.OrderId;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.UserId;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.ProductService;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.ProductResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

@Component
public class OrderFacade {

    private final OrderService orderService;
    private final ProductService productService;

    public OrderFacade(OrderService orderService, ProductService productFacade) {
        this.orderService = orderService;
        this.productService = productFacade;
    }

    public OrderResponse createOrder (Long userId, Long productId) {
        // RestClient로 productId, userId 통신으로 OrderedProduct orderedProduct
        ProductResponse product = productService.findProduct(productId);
        OrderedProduct orderedProduct = new OrderedProduct(
            new ProductId(product.getId()),
            product.getName(),
            product.getDescription(),
            product.getPrice()
        );
        return orderService.createOrder(new UserId(userId), orderedProduct);
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
