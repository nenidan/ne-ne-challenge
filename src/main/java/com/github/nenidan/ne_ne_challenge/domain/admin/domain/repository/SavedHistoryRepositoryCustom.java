package com.github.nenidan.ne_ne_challenge.domain.admin.domain.repository;

import com.github.nenidan.ne_ne_challenge.domain.admin.presentation.dto.request.LogSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.admin.presentation.dto.response.logs.PaymentHistoryResponse;

import java.util.List;

public interface SavedHistoryRepositoryCustom {
    List<PaymentHistoryResponse> findPaymentHistories(LogSearchCond cond);
}
