package com.github.nenidan.ne_ne_challenge.domain.payment.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.Payment;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PaymentRepositoryImpl implements PaymentRepository {

    private final JpaPaymentRepository jpaPaymentRepository;

    @Override
    public Payment save(Payment payment) {
        return jpaPaymentRepository.save(payment);
    }

    @Override
    public List<Payment> searchPayments(Long userId, Long cursor, String method, String status,
        LocalDateTime startDate, LocalDateTime endDate, int limit) {
        return jpaPaymentRepository.searchPayments(userId, cursor, method, status, startDate, endDate, limit);
    }
}
