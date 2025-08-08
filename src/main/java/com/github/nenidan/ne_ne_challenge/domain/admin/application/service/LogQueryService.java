package com.github.nenidan.ne_ne_challenge.domain.admin.application.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.request.LogSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs.AopLogResponse;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs.LogsResponse;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs.PaymentHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.repository.AopLogRepository;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.respository.SavedHistoryRepositoryImpl;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LogQueryService {

    private final AopLogRepository aopLogRepository;

    private final SavedHistoryRepositoryImpl savedHistoryRepository;


    private List<LogsResponse> mergeAndSort(List<? extends LogsResponse> aopLogs,
                                            List<? extends LogsResponse> histories,
                                            int limit) {
        List<LogsResponse> merged = new ArrayList<>();
        merged.addAll(aopLogs);
        merged.addAll(histories);
        merged.sort(Comparator.comparing(LogsResponse::getCreatedAt).reversed());
        return merged;
    }

    /*
    public CursorResponse<LogsResponse, LocalDateTime> getChallengeLogs(LogSearchCond cond) {
        int size = cond.getSize();

        List<AopLogResponse> aopLogs = aopLogRepository.findLogs(LogType.CHALLENGE, cond);
        List<ChallengeHistoryResponse> histories = savedHistoryRepository.findChallengeHistories(cond);

        List<LogsResponse> merged = mergeAndSort(aopLogs, histories, cond.getSize());

        //페이징
        boolean hasNext = merged.size() > size;
        List<LogsResponse> content = hasNext ? merged.subList(0, size) : merged;
        LocalDateTime nextCursor = hasNext ? content.get(content.size() - 1).getCreatedAt() : null;

        return CursorResponse.of(content, nextCursor, hasNext);
    }*/

    public CursorResponse<LogsResponse, LocalDateTime> getPaymentLogs(LogSearchCond cond) {
        int size = cond.getSize();

        List<AopLogResponse> aopLogs = aopLogRepository.findLogs(DomainType.PAYMENT, cond).stream().map(AopLogResponse::fromModel)
                .collect(Collectors.toList());

        List<PaymentHistoryResponse> histories = savedHistoryRepository.findPaymentHistories(cond);

        List<LogsResponse> merged = mergeAndSort(aopLogs, histories, cond.getSize());

        //페이징
        boolean hasNext = merged.size() > size;
        List<LogsResponse> content = hasNext ? merged.subList(0, size) : merged;
        LocalDateTime nextCursor = hasNext ? content.get(content.size() - 1).getCreatedAt() : null;

        return CursorResponse.of(content, nextCursor, hasNext);
    }
/*
    public CursorResponse<LogsResponse, LocalDateTime> getPointLogs(LogSearchCond cond) {
        int size = cond.getSize();

        List<AopLogResponse> aopLogs = aopLogRepository.findLogs(LogType.POINT, cond);
        List<PointHistoryResponse> histories = savedHistoryRepository.findPointHistories(cond);

        List<LogsResponse> merged = mergeAndSort(aopLogs, histories, cond.getSize());

        //페이징
        boolean hasNext = merged.size() > size;
        List<LogsResponse> content = hasNext ? merged.subList(0, size) : merged;
        LocalDateTime nextCursor = hasNext ? content.get(content.size() - 1).getCreatedAt() : null;

        return CursorResponse.of(content, nextCursor, hasNext);
    }

    public CursorResponse<LogsResponse, LocalDateTime> getUserLogs(LogSearchCond cond) {
        int size = cond.getSize();

        List<AopLogResponse> aopLogs = aopLogRepository.findLogs(LogType.USER, cond);
        List<UserHistoryResponse> histories = savedHistoryRepository.findUserHistories(cond);

        List<LogsResponse> merged = mergeAndSort(aopLogs, histories, cond.getSize());

        //페이징
        boolean hasNext = merged.size() > size;
        List<LogsResponse> content = hasNext ? merged.subList(0, size) : merged;
        LocalDateTime nextCursor = hasNext ? content.get(content.size() - 1).getCreatedAt() : null;

        return CursorResponse.of(content, nextCursor, hasNext);
    }*/


}
