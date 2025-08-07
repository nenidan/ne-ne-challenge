package com.github.nenidan.ne_ne_challenge.domain.challenge.application.query;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.request.ChallengeSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.request.HistorySearchCond;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.response.ChallengeHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.response.ChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.response.ChallengeSuccessRateResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.repository.ChallengeHistoryQueryRepository;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.repository.ChallengeQueryRepository;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeException;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ChallengeQueryService {

    private final ChallengeQueryRepository challengeRepository;
    private final ChallengeHistoryQueryRepository historyRepository;

    public ChallengeResponse findChallengeById(Long challengeId) {
        return challengeRepository.findChallengeById(challengeId)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.CHALLENGE_NOT_FOUND));
    }

    public CursorResponse<ChallengeResponse, LocalDateTime> getChallengeList(ChallengeSearchCond cond) {
        return CursorResponse.of(challengeRepository.findChallenges(cond), ChallengeResponse::getCreatedAt, cond.getSize());
    }

    public ChallengeHistoryResponse findHistoryById(Long historyId) {
        return historyRepository.findHistoryById(historyId)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.HISTORY_NOT_FOUND));
    }

    public CursorResponse<ChallengeHistoryResponse, LocalDateTime> getHistoryList(Long challengeId, HistorySearchCond cond) {
        return CursorResponse.of(historyRepository.findHistory(challengeId, cond), ChallengeHistoryResponse::getCreatedAt, cond.getSize());
    }

    public ChallengeSuccessRateResponse getSuccessRate(Long userId, Long challengeId) {
        ChallengeResponse challengeResponse = challengeRepository.findChallengeById(challengeId)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.CHALLENGE_NOT_FOUND));

        if(challengeResponse.getStatus() != ChallengeStatus.ONGOING) {
            throw new ChallengeException(ChallengeErrorCode.NOT_ONGOING);
        }

        int successfulDays = historyRepository.countByChallengeIdAndUserId(challengeId, userId).intValue();

        LocalDate today = LocalDate.now();
        LocalDate startDate = challengeResponse.getStartAt();
        LocalDate dueDate = challengeResponse.getDueAt();
        LocalDate endDate = today.isAfter(dueDate) ? dueDate : today;

        long daysFromStart = ChronoUnit.DAYS.between(startDate, endDate);
        long totalPeriod = daysFromStart + 1; // 오늘 포함

        return new ChallengeSuccessRateResponse((int) ((successfulDays * 100) / totalPeriod));
    }
}
