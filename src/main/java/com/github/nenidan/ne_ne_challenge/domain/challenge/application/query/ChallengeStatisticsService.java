package com.github.nenidan.ne_ne_challenge.domain.challenge.application.query;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.inner.InnerChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.inner.InnerHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.inner.InnerParticipantResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.mapper.InnerChallengeMapper;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository.ChallengeRepository;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository.HistoryReposiroty;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 내부 조회용으로 ResponseDto를 반환해주는 서비스
 */
//@Service
//@RequiredArgsConstructor
//public class ChallengeStatisticsService {
//
//    private final ChallengeRepository challengeRepository;
//    private final ParticipantRepository participantRepository;
//    private final HistoryReposiroty historyReposiroty;
//    private final InnerChallengeMapper innerChallengeMapper = InnerChallengeMapper.INSTANCE;
//
//    public List<InnerChallengeResponse> getAllChallenge() {
//        return challengeRepository.findAll().stream().map(innerChallengeMapper::toResponse).toList();
//    }
//
//    public List<InnerParticipantResponse> getAllParticipant() {
//        return participantRepository.findAll().stream().map(innerChallengeMapper::toResponse).toList();
//    }
//
//    public List<InnerHistoryResponse> getAllHistory() {
//        return historyReposiroty.findAll().stream().map(innerChallengeMapper::toResponse).toList();
//    }
//}
