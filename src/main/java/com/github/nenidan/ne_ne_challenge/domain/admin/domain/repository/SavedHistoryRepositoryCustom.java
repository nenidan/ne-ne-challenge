package com.github.nenidan.ne_ne_challenge.domain.admin.domain.repository;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.request.LogSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs.PaymentHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs.PointHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs.UserHistoryResponse;

public interface SavedHistoryRepositoryCustom {
    List<PaymentHistoryResponse> findPaymentHistories(LogSearchCond cond);
    List<PointHistoryResponse> findPointHistories(LogSearchCond cond);
    List<UserHistoryResponse> findUserHistories(LogSearchCond cond);
}
