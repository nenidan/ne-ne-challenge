package com.github.nenidan.ne_ne_challenge.domain.payment.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.payment.dto.request.ChargePointRequest;
import com.github.nenidan.ne_ne_challenge.domain.payment.dto.response.PaymentResponse;
import com.github.nenidan.ne_ne_challenge.domain.payment.entity.Payment;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentException;
import com.github.nenidan.ne_ne_challenge.domain.payment.repository.PaymentRepository;
import com.github.nenidan.ne_ne_challenge.domain.payment.type.PaymentMethod;
import com.github.nenidan.ne_ne_challenge.domain.payment.type.PaymentStatus;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserException;
import com.github.nenidan.ne_ne_challenge.domain.user.repository.UserRepository;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Transactional
    public PaymentResponse chargePoint(Long userId, ChargePointRequest chargePointRequest) {

        User findUser = userRepository.findById(userId)
            .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Payment payment = new Payment(findUser, chargePointRequest.getAmount(), chargePointRequest.getMethod());
        paymentRepository.save(payment);

        try {
            // TODO : 생성된 pointWallet(회원가입 시 pointWallet 생성)에 포인트 증감 로직
            payment.succeed();
        } catch (Exception e) {
            payment.fail();
            throw new PaymentException(PaymentErrorCode.CHARGE_FAILED, e);
        }

        return PaymentResponse.from(payment);
    }

    public CursorResponse searchMyPayments(Long userId, Long cursor, int size, PaymentMethod method,
        PaymentStatus status, LocalDate startDate, LocalDate endDate) {

        LocalDateTime start = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime end = (endDate != null) ? endDate.atTime(23, 59, 59) : null;

        User findUser = userRepository.findById(userId)
            .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        List<PaymentResponse> paymentList = paymentRepository.searchPayments(findUser.getId(), cursor, size + 1, method, status, start, end)
            .stream()
            .map(PaymentResponse::from)
            .toList();

        boolean hasNext = paymentList.size() > size;

        List<PaymentResponse> content = hasNext ? paymentList.subList(0, size) : paymentList;

        Long nextCursor = hasNext ? paymentList.get(paymentList.size() - 1).getPaymentId() : null;

        return new CursorResponse<>(content, nextCursor, hasNext);
    }
}
