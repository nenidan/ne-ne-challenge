package com.github.nenidan.ne_ne_challenge.domain.challenge.application.query;

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
