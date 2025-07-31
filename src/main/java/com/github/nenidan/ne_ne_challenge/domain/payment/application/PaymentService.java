package com.github.nenidan.ne_ne_challenge.domain.payment.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.ChargePointCommand;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.mapper.PaymentApplicationMapper;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.Payment;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.repository.PaymentRepository;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.type.PaymentMethod;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public Payment createChargePayment(Long userId, ChargePointCommand command) {

        PaymentMethod method = PaymentMethod.of(command.getMethod());

        Payment payment = Payment.createChargePayment(userId, command.getAmount(), method);

        return paymentRepository.save(payment);
    }

    public CursorResponse<PaymentResult, Long> searchMyPayments(Long userId, Long cursor, int size, String method,
        String status, LocalDate startDate, LocalDate endDate) {

        LocalDateTime cleanStartDate = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime cleanEndDate = (endDate != null) ? endDate.atTime(23, 59, 59) : null;
        String cleanMethod = StringUtils.hasText(method) ? method : null;
        String cleanStatus = StringUtils.hasText(status) ? status : null;

        List<PaymentResult> paymentList = paymentRepository.searchPayments(userId, cursor, cleanMethod, cleanStatus, cleanStartDate, cleanEndDate, size + 1)
            .stream()
            .map(PaymentApplicationMapper::toPaymentResult)
            .toList();

        boolean hasNext = paymentList.size() > size;

        List<PaymentResult> content = hasNext ? paymentList.subList(0, size) : paymentList;

        Long nextCursor = hasNext ? paymentList.get(paymentList.size() - 1).getId() : null;

        return new CursorResponse<>(content, nextCursor, hasNext);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void succeedPayment(Payment payment) {
        payment.succeed();
        paymentRepository.save(payment);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void failPayment(Payment payment) {
        payment.fail();
        paymentRepository.save(payment);
    }


}

