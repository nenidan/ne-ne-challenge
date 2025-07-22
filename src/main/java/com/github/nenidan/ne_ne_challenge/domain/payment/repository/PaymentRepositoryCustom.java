package com.github.nenidan.ne_ne_challenge.domain.payment.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.payment.entity.Payment;
import com.github.nenidan.ne_ne_challenge.domain.payment.type.PaymentMethod;
import com.github.nenidan.ne_ne_challenge.domain.payment.type.PaymentStatus;

public interface PaymentRepositoryCustom {
    List<Payment> searchPayments(
        Long userId,
        Long cursor,
        int limit,
        PaymentMethod method,
        PaymentStatus status,
        LocalDateTime startDate,
        LocalDateTime endDate);
}
