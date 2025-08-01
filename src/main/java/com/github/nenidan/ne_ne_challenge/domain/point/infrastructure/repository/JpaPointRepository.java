package com.github.nenidan.ne_ne_challenge.domain.point.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.nenidan.ne_ne_challenge.domain.point.domain.Point;

public interface JpaPointRepository extends JpaRepository<Point, Long> {

    @Query("""
        SELECT p FROM Point p
        WHERE p.pointWallet.id = :walletId
          AND p.remainingAmount > 0
          AND p.canceledAt IS NULL
        ORDER BY p.id ASC
    """)
    List<Point> findUsablePointsByWalletId(@Param("walletId") Long walletId);

    Optional<Point> findBySourceOrderId(String sourceOrderId);
}
