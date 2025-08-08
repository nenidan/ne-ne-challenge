package com.github.nenidan.ne_ne_challenge.domain.admin.domain.repository;

import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.request.LogSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs.PaymentHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs.PointHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs.UserHistoryResponse;

import java.util.List;

public interface SavedHistoryRepositoryCustom {
    List<PaymentHistoryResponse> findPaymentHistories(LogSearchCond cond);
    List<PointHistoryResponse> findPointHistories(LogSearchCond cond);
    List<UserHistoryResponse> findUserHistories(LogSearchCond cond);
}
