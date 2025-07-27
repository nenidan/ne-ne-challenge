package com.github.nenidan.ne_ne_challenge.domain.payment.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.Payment;

public interface PaymentRepository{

    Payment save(Payment payment);

    List<Payment> searchPayments(
        Long userId,
        Long cursor,
        String method,
        String status,
        LocalDateTime startDate,
        LocalDateTime endDate,
        int limit
    );
}
