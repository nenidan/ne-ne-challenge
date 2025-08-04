package com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.mapper;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.model.Order;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.type.OrderStatus;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.OrderDetail;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.entity.OrderDetailEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.entity.OrderEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.OrderDetailId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.OrderId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;

public class OrderMapper {

    public static OrderEntity toEntity(Order order) {
        return new OrderEntity(
            order.getOrderId() == null ? null : order.getOrderId().getValue(),
            order.getUserid().getValue(),
            new OrderDetailEntity(
                order.getOrderDetail().getOrderDetailId() == null ? null : order.getOrderDetail().getOrderDetailId().getValue(),
                order.getOrderDetail().getProductId().getValue(),
                order.getOrderDetail().getNameAtOrder(),
                order.getOrderDetail().getDescriptionAtOrder(),
                order.getOrderDetail().getPriceAtOrder(),
                order.getOrderDetail().getQuantity(),
                order.getOrderDetail().getDeletedAt()
            ),
            order.getOrderStatus(),
            order.getDeletedAt()
        );
    }

    public static Order toDomain(OrderEntity orderEntity) {
        return new Order(
            new OrderId(orderEntity.getId()),
            new UserId(orderEntity.getUserId()),
            new OrderDetail(
                new OrderDetailId(orderEntity.getOrderDetailEntity().getId()),
                new ProductId(orderEntity.getOrderDetailEntity().getProductId()),
                orderEntity.getOrderDetailEntity().getProductName(),
                orderEntity.getOrderDetailEntity().getProductDescription(),
                orderEntity.getOrderDetailEntity().getPriceAtOrder(),
                orderEntity.getOrderDetailEntity().getQuantity()
            ),
            orderEntity.getStatus(),
            orderEntity.getDeletedAt()
        );
    }

    public static Order toProjection(OrderFlatProjection orderFlatProjection) {
        return new Order(
            new OrderId(orderFlatProjection.getOrderId()),
            new UserId(orderFlatProjection.getUserId()),
            new OrderDetail(
                new OrderDetailId(orderFlatProjection.getOrderDetailId()),
                new ProductId(orderFlatProjection.getProductId()),
                orderFlatProjection.getProductName(),
                orderFlatProjection.getProductDescription(),
                orderFlatProjection.getPriceAtOrder(),
                orderFlatProjection.getQuantity()
            ),
            OrderStatus.valueOf(orderFlatProjection.getStatus()),
            orderFlatProjection.getDeletedAt()
        );
    }
}
