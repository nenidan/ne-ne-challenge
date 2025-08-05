package com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.entity.ProductEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.entity.QProductEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductQueryDslRepositoryImpl implements ProductQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductEntity> findAllProductsBy(Long cursor, String keyword, int limit) {
        QProductEntity productEntity = QProductEntity.productEntity;

        return queryFactory.selectFrom(productEntity)
            .where(
                productEntity.deletedAt.isNull(),
                cursor == null ? null : productEntity.id.loe(cursor),
                keyword == null ? null : productEntity.productName.like("%" + keyword + "%")
            )
            .orderBy(productEntity.id.desc())
            .limit(limit)
            .fetch();
    }
}
