package com.github.nenidan.ne_ne_challenge.domain.point.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.nenidan.ne_ne_challenge.domain.point.entity.PointTransaction;
import com.github.nenidan.ne_ne_challenge.domain.point.type.PointReason;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long>{

    @Query(value = "SELECT * FROM point_transaction p " +
        "WHERE p.point_wallet_id = :pointWalletId " +
        "AND (:cursor IS NULL OR p.id <= :cursor) " +
        "AND (:reason IS NULL OR p.reason = :reason) " +
        "AND (:startDate IS NULL OR p.created_at >= :startDate) " +
        "AND (:endDate IS NULL OR p.created_at <= :endDate) " +
        "ORDER BY p.id DESC " +
        "LIMIT :limit",
        nativeQuery = true)
    List<PointTransaction> searchMyPointHistory(
        @Param("pointWalletId") Long pointWalletId,
        @Param("cursor") Long cursor,
        @Param("reason") PointReason reason,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        @Param("limit") int limit
    );
}
