package com.github.nenidan.ne_ne_challenge.domain.admin.respository;

import com.github.nenidan.ne_ne_challenge.domain.admin.dto.request.DashboardSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.admin.dto.response.PaymentHistoryResponse;

import java.util.List;

public interface SavedHistoryRepositoryCustom {
    List<PaymentHistoryResponse> findPaymentHistories(DashboardSearchCond cond);
}
