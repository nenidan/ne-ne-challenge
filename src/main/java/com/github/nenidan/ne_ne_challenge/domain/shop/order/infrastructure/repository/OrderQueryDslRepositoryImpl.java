package com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.entity.QOrderDetailEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.entity.QOrderEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.mapper.OrderFlatProjection;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderQueryDslRepositoryImpl implements OrderQueryDslRepository{

    private final JPAQueryFactory queryFactory;

    public List<OrderFlatProjection> findAllOrdersBy(Long userId, Long cursor, String keyword, int limit ) {
        QOrderEntity orderEntity = QOrderEntity.orderEntity;
        QOrderDetailEntity orderDetailEntity = QOrderDetailEntity.orderDetailEntity;

        BooleanExpression cursorExpression = cursor == null ? null : orderEntity.id.loe(cursor);
        BooleanExpression keywordExpression = keyword == null ? null : orderDetailEntity.productName.like("%" + keyword + "%");

        return queryFactory.select(
            Projections.constructor(
                OrderFlatProjection.class,
                orderEntity.id,
                orderEntity.userId,
                orderEntity.status,
                orderEntity.deletedAt,
                orderDetailEntity.id,
                orderDetailEntity.productName,
                orderDetailEntity.productDescription,
                orderDetailEntity.priceAtOrder,
                orderDetailEntity.quantity
                )
            )
            .distinct()
            .from(orderEntity)
            .fetchJoin()
            .where(
                orderEntity.userId.eq(userId),
                cursorExpression,
                keywordExpression
            )
            .orderBy(orderEntity.id.desc())
            .limit(limit)
            .fetch();
    }
}
