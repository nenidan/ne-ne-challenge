package com.github.nenidan.ne_ne_challenge.domain.challenge.service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.ChallengeSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.CreateChallengeRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.UpdateChallengeRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response.ChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response.inner.InnerChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.ChallengeUser;
import com.github.nenidan.ne_ne_challenge.domain.challenge.exception.ChallengeErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.challenge.exception.ChallengeException;
import com.github.nenidan.ne_ne_challenge.domain.challenge.repository.ChallengeRepository;
import com.github.nenidan.ne_ne_challenge.domain.challenge.repository.ChallengeUserRepository;
import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeStatus;
import com.github.nenidan.ne_ne_challenge.domain.point.entity.PointWallet;
import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointException;
import com.github.nenidan.ne_ne_challenge.domain.point.repository.PointWalletRepository;
import com.github.nenidan.ne_ne_challenge.domain.user.dto.response.UserResponse;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserException;
import com.github.nenidan.ne_ne_challenge.domain.user.repository.UserRepository;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.github.nenidan.ne_ne_challenge.domain.challenge.exception.ChallengeErrorCode.*;
import static com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeStatus.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeUserService challengeUserService;

    private final ChallengeHistoryService challengeHistoryService;

    private final ChallengeRepository challengeRepository;

    private final UserRepository userRepository;

    private final ChallengeUserRepository challengeUserRepository;

    private final PointWalletRepository pointWalletRepository;

    @Transactional
    public ChallengeResponse createChallenge(CreateChallengeRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Challenge newChallenge = request.toEntity();
        Challenge savedChallenge = challengeRepository.save(newChallenge);
        challengeUserService.joinChallenge(user.getId(), savedChallenge.getId(), true);

        return ChallengeResponse.from(savedChallenge);
    }

    public ChallengeResponse getChallenge(Long id) {
        Challenge foundChallenge = challengeRepository.findById(id)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.CHALLENGE_NOT_FOUND));

        return ChallengeResponse.from(foundChallenge);
    }

    public CursorResponse<ChallengeResponse, LocalDateTime> getChallengeList(ChallengeSearchCond cond) {
        int size = cond.getSize();

        List<ChallengeResponse> challengeList = challengeRepository.getChallengeList(cond.getUserId(),
            cond.getName(),
            cond.getStatus(),
            cond.getDueAt(),
            cond.getCategory(),
            cond.getMaxParticipationFee(),
            cond.getCursor(),
            size + 1
        ).stream().map(ChallengeResponse::from).toList();

        boolean hasNext = challengeList.size() > size;
        List<ChallengeResponse> content = hasNext ? challengeList.subList(0, size) : challengeList;
        LocalDateTime nextCursor = hasNext ? challengeList.get(challengeList.size() - 1).getCreatedAt() : null;

        return CursorResponse.of(content, nextCursor, hasNext);
    }

    /**
     * 챌린지 상태 수정: WAITING → ONGOING만 가능
     * name, description, category는 자유롭게 변경 가능
     * dueAt 은 오늘보다 이전으로 설정 불가
     * minParticipants <= maxParticipants
     * 참가비는 생성 후 시작 불가
     * 불가능한 조건이 존재할 시 모든 변경 취소
     *
     * @param userId:      요청자 id
     * @param challengeId: 챌린지 id
     * @param request:     바꿀 정보를 담고 있는 DTO
     * @return 변경된 챌린지 정보
     */
    @Transactional
    public ChallengeResponse updateChallenge(Long userId, Long challengeId, UpdateChallengeRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        Challenge challenge = challengeRepository.findById(challengeId)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.CHALLENGE_NOT_FOUND));
        ChallengeUser challengeUser = challengeUserRepository.findByUserAndChallenge(user, challenge)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.NOT_PARTICIPATING));

        if (!challengeUser.isHost()) {
            throw new ChallengeException(ChallengeErrorCode.NOT_HOST);
        }

        challenge.safeUpdate(request.getName(), request.getDescription(), request.getCategory());
        challenge.setDueDate(request.getDueAt());

        int participantsCount = challengeUserRepository.countByChallenge(challenge);
        if (request.getMinParticipants() != null || request.getMaxParticipants() != null) {
            challenge.updateParticipantsLimit(participantsCount,
                request.getMinParticipants(),
                request.getMaxParticipants()
            );
        }

        // 챌린지 진행 상태 변경은 다른 수정이 모두 반영된 뒤에 수행
        ChallengeStatus newStatus = request.getStatus();
        ChallengeStatus currentStatus = challenge.getStatus();
        if (newStatus != null) {
            if (newStatus == WAITING) {
                if (currentStatus != WAITING) {
                    throw new ChallengeException(INVALID_STATUS_TRANSITION);
                }
            } else if (newStatus == ONGOING) {
                challenge.start(participantsCount);
            } else if (newStatus == FINISHED) {
                challenge.finish();
                distributeTotalFeeToWinners(challenge.getId(), challenge.getTotalFee());
            }
        }

        return ChallengeResponse.from(challenge);
    }

    private void distributeTotalFeeToWinners(long challengeId, int totalFee) {
        List<Long> winners = getWinners(challengeId);

        if (winners.isEmpty()) return;

        int reward = totalFee / winners.size();
        for (Long winner : winners) {
            PointWallet pointWallet = pointWalletRepository.findById(winner)
                .orElseThrow(() -> new PointException(PointErrorCode.POINT_WALLET_NOT_FOUND)); // fixme
            pointWallet.increase(reward);
        }
    }

    private List<Long> getWinners(Long challengeId) {
        Long cursor = 1L;
        List<Long> participantIdList;
        Long nextCursor;
        List<Long> winners = new ArrayList<>();

        do {
            CursorResponse<UserResponse, Long> response = challengeUserService.getChallengeParticipantList(challengeId,
                cursor,
                20
            );
            nextCursor = response.getNextCursor();
            participantIdList = response.getContent().stream().map(UserResponse::getId).toList();

            for (Long participantId : participantIdList) {
                int successRate = challengeHistoryService.getSuccessRate(participantId, challengeId).getSuccessRate();
                if (successRate >= 70) {
                    winners.add(participantId);
                }
            }

            cursor = nextCursor;
        } while (cursor != null);

        return winners;
    }



    // todo: 여러 번의 리포지토리 호출 조인으로 한 번에? 엔티티 연관관계부터 수정 필요할 듯
    @Transactional
    public Void deleteChallenge(Long userId, Long challengeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        Challenge challenge = challengeRepository.findById(challengeId)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.CHALLENGE_NOT_FOUND));
        ChallengeUser challengeUser = challengeUserRepository.findByUserAndChallenge(user, challenge)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.NOT_PARTICIPATING));

        if (!challengeUser.isHost()) {
            throw new ChallengeException(ChallengeErrorCode.NOT_HOST);
        }

        challenge.delete();
        return null;
    }

    // 초기 통계값 개발을 위한 전체 데이터 반환 메소드
    public List<InnerChallengeResponse> getAllChallengeList() {
        return challengeRepository.findAll().stream() // 메모리 부족 주의
            .map(InnerChallengeResponse::from).toList();
    }

    @Transactional
    public ChallengeResponse joinChallenge(Long userId, Long challengeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        Challenge challenge = challengeRepository.findById(challengeId)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.CHALLENGE_NOT_FOUND));
        boolean alreadyParticipated = challengeUserRepository.existsByUserAndChallenge(user, challenge);
        if (alreadyParticipated) throw new ChallengeException(ALREADY_PARTICIPATED);

        challengeUserService.joinChallenge(userId, challengeId, false);

        return ChallengeResponse.from(challenge);
    }
}