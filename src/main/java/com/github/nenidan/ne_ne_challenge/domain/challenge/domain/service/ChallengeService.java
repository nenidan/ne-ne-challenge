package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.dto.ChallengeInfo;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeException;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    public Challenge createChallenge(ChallengeInfo info) {
        Challenge newChallenge = Challenge.from(info); // Todo 생성 시 정상값 보장 필요
        return challengeRepository.save(newChallenge);
    }

    public Challenge getChallenge(Long challengeId) {
        return challengeRepository.findById(challengeId)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.CHALLENGE_NOT_FOUND));
    }

    public void delete(Challenge challenge) {
        challenge.delete();
        challengeRepository.save(challenge);
    }

    public void increaseTotalFee(Challenge challenge) {
        challenge.increaseTotalFee();
        challengeRepository.save(challenge);
    }

    List<Challenge> getChallengeList(
        Long userId,
        String name,
        ChallengeStatus status,
        LocalDate dueAt,
        ChallengeCategory category,
        Integer maxParticipationFee,
        LocalDateTime cursor,
        int limit
    ) {
        return challengeRepository.getChallengeList(userId, name, status, dueAt, category, maxParticipationFee, cursor, limit);
    }
}
