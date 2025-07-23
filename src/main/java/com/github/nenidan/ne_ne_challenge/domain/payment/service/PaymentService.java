package com.github.nenidan.ne_ne_challenge.domain.payment.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.payment.dto.request.ChargePointRequest;
import com.github.nenidan.ne_ne_challenge.domain.payment.dto.response.PaymentResponse;
import com.github.nenidan.ne_ne_challenge.domain.payment.entity.Payment;
import com.github.nenidan.ne_ne_challenge.domain.payment.repository.PaymentRepository;
import com.github.nenidan.ne_ne_challenge.domain.payment.type.PaymentMethod;
import com.github.nenidan.ne_ne_challenge.domain.payment.type.PaymentStatus;
import com.github.nenidan.ne_ne_challenge.domain.point.entity.PointTransaction;
import com.github.nenidan.ne_ne_challenge.domain.point.entity.PointWallet;
import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointException;
import com.github.nenidan.ne_ne_challenge.domain.point.repository.PointTransactionRepository;
import com.github.nenidan.ne_ne_challenge.domain.point.repository.PointWalletRepository;
import com.github.nenidan.ne_ne_challenge.domain.point.type.PointReason;
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
    private final PointWalletRepository pointWalletRepository;
    private final PointTransactionRepository pointTransactionRepository;

    @Transactional
    public PaymentResponse chargePoint(Long userId, ChargePointRequest chargePointRequest) {

        User foundUser = userRepository.findById(userId)
            .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Payment payment = new Payment(foundUser, chargePointRequest.getAmount(), chargePointRequest.getMethod());
        paymentRepository.save(payment);


        try {
            PointWallet pointWallet = pointWalletRepository.findByUserId(foundUser.getId())
                .orElseThrow(() -> new PointException(PointErrorCode.POINT_WALLET_NOT_FOUND));
            pointWallet.increase(chargePointRequest.getAmount());

            PointTransaction pointTransaction = PointTransaction.createChargeTransaction(pointWallet, chargePointRequest.getAmount(), PointReason.CHARGE, "포인트 구매가 완료되었습니다.");
            pointTransactionRepository.save(pointTransaction);

            payment.succeed();
        } catch (Exception e) {
            payment.fail();
        }

        return PaymentResponse.from(payment);
    }

    public CursorResponse<PaymentResponse, Long> searchMyPayments(Long userId, Long cursor, int size, PaymentMethod method,
        PaymentStatus status, LocalDate startDate, LocalDate endDate) {

        LocalDateTime start = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime end = (endDate != null) ? endDate.atTime(23, 59, 59) : null;

        User findUser = userRepository.findById(userId)
            .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        List<PaymentResponse> paymentList = paymentRepository.searchPayments(findUser.getId(), cursor, method, status, start, end, size + 1)
            .stream()
            .map(PaymentResponse::from)
            .toList();

        boolean hasNext = paymentList.size() > size;

        List<PaymentResponse> content = hasNext ? paymentList.subList(0, size) : paymentList;

        Long nextCursor = hasNext ? paymentList.get(paymentList.size() - 1).getPaymentId() : null;

        return new CursorResponse<>(content, nextCursor, hasNext);
    }


}
