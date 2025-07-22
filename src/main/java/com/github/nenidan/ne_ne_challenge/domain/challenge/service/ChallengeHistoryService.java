package com.github.nenidan.ne_ne_challenge.domain.challenge.service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.CreateHistoryRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response.ChallengeHistoryResponse;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
    public ChallengeHistoryResponse createHistory(CreateHistoryRequest request, Long userId, Long challengeId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        Challenge challenge = challengeRepository.findById(challengeId)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.CHALLENGE_NOT_FOUND));
        ChallengeUser challengeUser = challengeUserRepository.findByUserAndChallenge(user, challenge)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.NOT_MY_CHALLENGE));

        LocalDateTime startOfDay = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        Long count = challengeHistoryRepository.countTodayHistory(userId, challengeId, startOfDay, endOfDay);
        if (count > 0) {
            throw new ChallengeException(ALREADY_VERIFIED);
        }

        ChallengeHistory newHistory = new ChallengeHistory(user, challenge, request.getContent(), true); // Todo: 현재는 그냥 성공처리

        ChallengeHistory savedHistory = challengeHistoryRepository.save(newHistory);
        return ChallengeHistoryResponse.from(savedHistory);
    }
}