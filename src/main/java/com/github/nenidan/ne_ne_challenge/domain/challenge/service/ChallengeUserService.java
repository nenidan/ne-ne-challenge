package com.github.nenidan.ne_ne_challenge.domain.challenge.service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response.inner.InnerChallengeUserResponse;
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

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeUserService {

    private final UserRepository userRepository;

    private final ChallengeRepository challengeRepository;

    private final ChallengeUserRepository challengeUserRepository;

    private final PointWalletRepository pointWalletRepository;

    @Transactional
    public void joinChallenge(long userId, long challengeId, boolean isHost) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        Challenge challenge = challengeRepository.findById(challengeId)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.CHALLENGE_NOT_FOUND));

        if (challenge.getStatus() != ChallengeStatus.WAITING)
            throw new ChallengeException(ChallengeErrorCode.NOT_WAITING);

        long currentParticipantCount = challengeUserRepository.countByChallengeId(challengeId);
        if(currentParticipantCount == challenge.getMaxParticipants()) {
            throw new ChallengeException(ChallengeErrorCode.FULL);
        }

        // 포인트를 직접 조회하고 예외를 던지는 것이 아니라 차감을 요청하고 결과를 받아야 책임 분리가 잘 된 구조
        // 현재 PointService가 차감 메소드를 제공하고 있지 않아 일단은 PointWalletRepository에서 직접 PointWallet 엔티티를 가져와서 처리해야 함
        // 심지어 예외도 포인트 도메인 예외를 던져야 함
        PointWallet pointWallet = pointWalletRepository.findByUserId(userId).orElseThrow(() -> new PointException(
            PointErrorCode.POINT_WALLET_NOT_FOUND));

        pointWallet.decrease(challenge.getParticipationFee());

        challenge.addParticipant(user);
        ChallengeUser challengeUser = new ChallengeUser(user, challenge, isHost);

        challengeUserRepository.save(challengeUser);
    }

    // 초기 통계값 개발을 위한 전체 데이터 반환 메소드
    public List<InnerChallengeUserResponse> getAllChallengeUserList() {
        return challengeUserRepository.findAll().stream() // 메모리 부족 주의
            .map(InnerChallengeUserResponse::from).toList();
    }

    public CursorResponse<UserResponse, Long> getChallengeParticipantList(Long id, Long cursor, int size) {
        Challenge challenge = challengeRepository.findById(id)
            .orElseThrow(() -> new ChallengeException(ChallengeErrorCode.CHALLENGE_NOT_FOUND));

        List<UserResponse> participantList = challengeUserRepository.getParticipantList(id, cursor, size)
            .stream()
            .map(UserResponse::from)
            .toList();

        boolean hasNext = participantList.size() > size;
        List<UserResponse> content = hasNext ? participantList.subList(0, size) : participantList;
        Long nextCursor = hasNext ? participantList.get(participantList.size() - 1).getId() : null;

        return CursorResponse.of(content, nextCursor, hasNext);
    }
}
