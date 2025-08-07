package com.github.nenidan.ne_ne_challenge.domain.point.infrastructure.repository;

import static com.github.nenidan.ne_ne_challenge.domain.point.domain.QPointTransaction.*;
import static com.github.nenidan.ne_ne_challenge.domain.point.domain.QPointWallet.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.point.domain.Point;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.PointTransaction;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.PointWallet;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.repository.PointRepository;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.type.PointReason;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepository {

    private final JpaPointWalletRepository jpaPointWalletRepository;
    private final JpaPointTransactionRepository jpaPointTransactionRepository;
    private final JpaPointRepository jpaPointRepository;
    private final JPAQueryFactory factory;

    @Override
    public PointTransaction save(PointTransaction pointTransaction) {
        return jpaPointTransactionRepository.save(pointTransaction);
    }

    @Override
    public List<PointTransaction> searchMyPointHistory(Long pointWalletId, Long cursor, PointReason reason,
        LocalDateTime startDate, LocalDateTime endDate, int limit) {

        BooleanBuilder whereClause = buildWhereCondition(pointWalletId, cursor, reason, startDate, endDate);

        return factory.selectFrom(pointTransaction)
            .where(whereClause)
            .orderBy(pointTransaction.id.desc())
            .limit(limit)
            .fetch();
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return jpaPointWalletRepository.existsByUserId(userId);
    }

    @Override
    public PointWallet save(PointWallet pointWallet) {
        return jpaPointWalletRepository.save(pointWallet);
    }

    @Override
    public Point save(Point point) {
        return jpaPointRepository.save(point);
    }

    @Override
    public Optional<PointWallet> findWalletByUserId(Long userId) {
        return jpaPointWalletRepository.findByUserId(userId);
    }

    @Override
    public Optional<Point> findBySourceOrderId(String orderId) {
        return jpaPointRepository.findBySourceOrderId(orderId);
    }

    @Override
    public List<Point> findUsablePointsByWalletId(Long walletId) {
        return jpaPointRepository.findUsablePointsByWalletId(walletId);
    }

    @Override
    public List<PointTransaction> findAll() {
        return jpaPointTransactionRepository.findAll();
    }

    // ================ 동적 검색 조건 ================
    private BooleanBuilder buildWhereCondition(Long pointWalletId, Long cursor, PointReason reason,
        LocalDateTime startDate, LocalDateTime endDate) {

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(pointWallet.pointWallet.id.eq(pointWalletId));

        if (cursor != null) {
            builder.and(pointTransaction.id.loe(cursor));
        }

        if (reason != null) {
            builder.and(pointTransaction.reason.eq(reason));
        }

        if (startDate != null && endDate != null) {
            builder.and(pointTransaction.updatedAt.between(startDate, endDate));
        } else if (startDate != null) {
            builder.and(pointTransaction.updatedAt.goe(startDate));
        } else if (endDate != null) {
            builder.and(pointTransaction.updatedAt.loe(endDate));
        }

        return builder;
    }
}
