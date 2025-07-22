package com.github.nenidan.ne_ne_challenge.domain.shop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.shop.dto.response.OrderResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.entity.Order;
import com.github.nenidan.ne_ne_challenge.domain.shop.entity.OrderDetail;
import com.github.nenidan.ne_ne_challenge.domain.shop.entity.Product;
import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopException;
import com.github.nenidan.ne_ne_challenge.domain.shop.repository.OrderRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.repository.ProductRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.type.OrderStatus;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserException;
import com.github.nenidan.ne_ne_challenge.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponse createOrder(Long userId, Long productId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ShopException(ShopErrorCode.PRODUCT_NOT_FOUND));

        Integer productPrice = product.getProductPrice();

        Order order = new Order(user);
        OrderDetail orderDetail = new OrderDetail(product, productPrice);

        order.addOrderDetail(orderDetail);

        Order saveOrder = orderRepository.save(order);

        return OrderResponse.fromEntity(saveOrder);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(()-> new ShopException(ShopErrorCode.ORDER_NOT_FOUND));

        order.delete();
    }
}
