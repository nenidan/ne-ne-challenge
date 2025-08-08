package com.github.nenidan.ne_ne_challenge.domain.admin.domain.repository;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.request.LogSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs.PaymentHistoryResponse;

public interface SavedHistoryRepositoryCustom {
    List<PaymentHistoryResponse> findPaymentHistories(LogSearchCond cond);
}
