package com.github.nenidan.ne_ne_challenge.domain.payment.infrastructure.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.Payment;

public interface JpaPaymentRepository extends JpaRepository<Payment, Long> {
}
