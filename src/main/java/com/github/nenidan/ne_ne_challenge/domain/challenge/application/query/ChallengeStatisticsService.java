package com.github.nenidan.ne_ne_challenge.domain.challenge.application.query;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.inner.InnerChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.inner.InnerHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.inner.InnerParticipantResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.repository.InnerChallengeQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 내부 조회용으로 ResponseDto를 반환해주는 서비스
 */
@Service
@RequiredArgsConstructor
public class ChallengeStatisticsService {

    private final InnerChallengeQueryRepository innerChallengeRepository;

    public List<InnerChallengeResponse> getAllChallenge() {
        return innerChallengeRepository.findAllChallenges();
    }

    public List<InnerParticipantResponse> getAllParticipant() {
        return innerChallengeRepository.findAllParticipants();
    }

    public List<InnerHistoryResponse> getAllHistory() {
        return innerChallengeRepository.findAllHistory();
    }
}
