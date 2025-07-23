package com.github.nenidan.ne_ne_challenge.domain.challenge.service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.CreateHistoryRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.HistorySearchCond;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response.ChallengeHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response.ChallengeSuccessRateResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.ChallengeHistory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.ChallengeUser;
import com.github.nenidan.ne_ne_challenge.domain.challenge.exception.ChallengeErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.challenge.exception.ChallengeException;
import com.github.nenidan.ne_ne_challenge.domain.challenge.repository.ChallengeHistoryRepository;
import com.github.nenidan.ne_ne_challenge.domain.challenge.repository.ChallengeRepository;
import com.github.nenidan.ne_ne_challenge.domain.challenge.repository.ChallengeUserRepository;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserException;
import com.github.nenidan.ne_ne_challenge.domain.user.repository.UserRepository;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.github.nenidan.ne_ne_challenge.domain.challenge.exception.ChallengeErrorCode.ALREADY_VERIFIED;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeHistoryService {

    private final UserRepository userRepository;

    private final ChallengeRepository challengeRepository;

    private final ChallengeUserRepository challengeUserRepository;

    private final ChallengeHistoryRepository challengeHistoryRepository;

    @Transactional
    public ChallengeHistoryResponse createHistory(CreateHistoryRequest request, Long challengeId) {
        Long userId = request.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(() -> new ChallengeException(
            ChallengeErrorCode.CHALLENGE_NOT_FOUND));
        ChallengeUser challengeUser = challengeUserRepository.findByUserAndChallenge(user,
            challenge
        ).orElseThrow(() -> new ChallengeException(ChallengeErrorCode.NOT_PARTICIPATING));

        LocalDateTime startOfDay = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        Long count = challengeHistoryRepository.countTodayHistory(userId, challengeId, startOfDay, endOfDay);
        if (count > 0) {
            throw new ChallengeException(ALREADY_VERIFIED);
        }

        ChallengeHistory newHistory = new ChallengeHistory(user,
            challenge,
            request.getContent(),
            true
        ); // Todo: 현재는 별도의 인증 절차 없이 성공 처리

        ChallengeHistory savedHistory = challengeHistoryRepository.save(newHistory);
        return ChallengeHistoryResponse.from(savedHistory);
    }

    public CursorResponse<ChallengeHistoryResponse, LocalDateTime> getHistoryList(Long challengeId,
        HistorySearchCond cond
    ) {
        Long userId = cond.getUserId();
        int size = cond.getSize();

        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(() -> new ChallengeException(
            ChallengeErrorCode.CHALLENGE_NOT_FOUND));
        ChallengeUser challengeUser = challengeUserRepository.findByUserAndChallenge(user,
            challenge
        ).orElseThrow(() -> new ChallengeException(ChallengeErrorCode.NOT_PARTICIPATING));

        List<ChallengeHistoryResponse> challengeHistoryList = challengeHistoryRepository.getChallengeHistoryList(userId,
            challengeId,
            cond.getCursor(),
            cond.getSize() + 1
        ).stream().map(ChallengeHistoryResponse::from).toList();

        boolean hasNext = challengeHistoryList.size() > size;
        List<ChallengeHistoryResponse> content = hasNext ? challengeHistoryList.subList(0, size) : challengeHistoryList;
        LocalDateTime nextCursor = hasNext ? challengeHistoryList.get(challengeHistoryList.size() - 1).getCreatedAt() : null;

        return CursorResponse.of(content, nextCursor, hasNext);
    }

    public ChallengeSuccessRateResponse getSuccessRate(Long userId, Long id) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        Challenge challenge = challengeRepository.findById(id)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.CHALLENGE_NOT_FOUND));
        if(challenge.getStartedAt() == null) {
            throw new ChallengeException(ChallengeErrorCode.NOT_STARTED);
        }
        ChallengeUser challengeUser = challengeUserRepository.findByUserAndChallenge(user, challenge)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.NOT_PARTICIPATING));

        long daysFromStart = ChronoUnit.DAYS.between(challenge.getStartedAt().toLocalDate(), LocalDateTime.now().toLocalDate());
        long totalPeriod = daysFromStart + 1; // 오늘 포함
        int successfulDays = challengeHistoryRepository.countByChallengeAndUserAndIsSuccessTrue(challenge, user);
        int successRate = (int) ((successfulDays * 100) / totalPeriod);

        return new ChallengeSuccessRateResponse(successRate);
    }
}