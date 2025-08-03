package com.github.nenidan.ne_ne_challenge.domain.payment.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.Payment;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.type.PaymentStatus;

public interface PaymentRepository{

    Payment save(Payment payment);

    List<Payment> searchPayments(
        Long userId,
        Long cursor,
        String status,
        LocalDateTime startDate,
        LocalDateTime endDate,
        int limit
    );

    Optional<Payment> findByOrderId(String orderId);
}
