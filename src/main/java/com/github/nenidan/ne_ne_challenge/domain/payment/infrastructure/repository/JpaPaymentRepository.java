package com.github.nenidan.ne_ne_challenge.domain.payment.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.Payment;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.type.PaymentStatus;

public interface JpaPaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "SELECT * FROM payment p " +
        "WHERE p.user_id = :userId " +
        "AND (:cursor IS NULL OR p.id <= :cursor) " +
        "AND (:status IS NULL OR p.status = :status) " +
        "AND (:startDate IS NULL OR p.updated_at >= :startDate) " +
        "AND (:endDate IS NULL OR p.updated_at <= :endDate) " +
        "ORDER BY p.id DESC " +
        "LIMIT :limit",
        nativeQuery = true)
    List<Payment> searchPayments(
        @Param("userId") Long userId,
        @Param("cursor") Long cursor,
        @Param("status") String status,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        @Param("limit") int limit
    );

    Optional<Payment> findByOrderId(String orderId);
}
