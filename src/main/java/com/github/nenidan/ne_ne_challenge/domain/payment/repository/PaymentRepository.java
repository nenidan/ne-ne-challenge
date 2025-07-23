package com.github.nenidan.ne_ne_challenge.domain.payment.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.nenidan.ne_ne_challenge.domain.payment.entity.Payment;
import com.github.nenidan.ne_ne_challenge.domain.payment.type.PaymentMethod;
import com.github.nenidan.ne_ne_challenge.domain.payment.type.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "SELECT * FROM payment p " +
        "WHERE p.user_id = :userId " +
        "AND (:cursor IS NULL OR p.id <= :cursor) " +
        "AND (:method IS NULL OR p.method = :method) " +
        "AND (:status IS NULL OR p.status = :status) " +
        "AND (:startDate IS NULL OR p.updated_at >= :startDate) " +
        "AND (:endDate IS NULL OR p.updated_at <= :endDate) " +
        "ORDER BY p.id DESC " +
        "LIMIT :limit",
        nativeQuery = true)
    List<Payment> searchPayments(
        @Param("userId") Long userId,
        @Param("cursor") Long cursor,
        @Param("method") PaymentMethod method,
        @Param("status") PaymentStatus status,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        @Param("limit") int limit
    );
}
