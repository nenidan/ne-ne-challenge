package com.github.nenidan.ne_ne_challenge.domain.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.nenidan.ne_ne_challenge.domain.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>, PaymentRepositoryCustom {
}
