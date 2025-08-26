package com.github.nenidan.ne_ne_challenge.domain.challenge.application.query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.request.ChallengeSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.request.HistorySearchCond;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.response.ChallengeHistoryDto;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.response.ChallengeDto;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.response.ChallengeSuccessRateDto;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.repository.ChallengeHistoryQueryRepository;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.repository.ChallengeQueryRepository;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeException;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository.ChallengeRepository;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChallengeQueryService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeQueryRepository queryRepository;
    private final ChallengeHistoryQueryRepository historyRepository;

    public ChallengeDto findChallengeById(Long challengeId) {
        return queryRepository.findChallengeById(challengeId)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.CHALLENGE_NOT_FOUND));
    }

    public CursorResponse<ChallengeDto, LocalDateTime> getChallengeList(ChallengeSearchCond cond) {
        return CursorResponse.of(queryRepository.findChallenges(cond), ChallengeDto::getCreatedAt, cond.getSize());
    }

    public ChallengeHistoryDto findHistoryById(Long historyId) {
        return historyRepository.findHistoryById(historyId)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.HISTORY_NOT_FOUND));
    }

    public CursorResponse<ChallengeHistoryDto, LocalDateTime> getHistoryList(Long challengeId, HistorySearchCond cond) {
        return CursorResponse.of(historyRepository.findHistory(challengeId, cond), ChallengeHistoryDto::getCreatedAt, cond.getSize());
    }

    public ChallengeSuccessRateDto getSuccessRate(Long userId, Long challengeId) {
        ChallengeDto challengeResponse = queryRepository.findChallengeById(challengeId)
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

        return new ChallengeSuccessRateDto((int) ((successfulDays * 100) / totalPeriod));
    }

    public CursorResponse<Long, Long> findParticipants(Long id, Long cursor, int size) {
        Challenge challenge = challengeRepository.findById(id)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.CHALLENGE_NOT_FOUND));

        List<Long> filtered = challenge.getParticipantIdList().stream()
            .filter(pid -> cursor == null || pid > cursor)
            .sorted()
            .collect(Collectors.toList());

        boolean hasNext = filtered.size() > size;
        List<Long> result = hasNext ? filtered.subList(0, size) : filtered;
        Long nextCursor = hasNext ? result.get(size - 1) : null;

        return new CursorResponse<>(result, nextCursor, hasNext);
    }
}
