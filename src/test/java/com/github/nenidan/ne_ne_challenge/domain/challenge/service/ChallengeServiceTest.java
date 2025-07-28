package com.github.nenidan.ne_ne_challenge.domain.challenge.service;

import static com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeStatus.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.CreateChallengeRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.UpdateChallengeRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response.ChallengeSuccessRateResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.ChallengeUser;
import com.github.nenidan.ne_ne_challenge.domain.challenge.repository.ChallengeRepository;
import com.github.nenidan.ne_ne_challenge.domain.challenge.repository.ChallengeUserRepository;
import com.github.nenidan.ne_ne_challenge.domain.point.entity.PointWallet;
import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointException;
import com.github.nenidan.ne_ne_challenge.domain.point.repository.PointWalletRepository;
import com.github.nenidan.ne_ne_challenge.domain.user.dto.response.UserResponse;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import com.github.nenidan.ne_ne_challenge.domain.user.repository.UserRepository;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

@ExtendWith(MockitoExtension.class)
class ChallengeServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    ChallengeUserRepository challengeUserRepository;

    @Mock
    PointWalletRepository pointWalletRepository;

    @Mock
    ChallengeHistoryService challengeHistoryService;

    @Mock
    ChallengeRepository challengeRepository;

    @Mock
    ChallengeUserService challengeUserService;

    @InjectMocks
    ChallengeService challengeService;

    @Test
    void 포인트_부족_시_챌린지_생성에_실패한다() {
        // given
        User user = mock(User.class);
        given(user.getId()).willReturn(1L);
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        CreateChallengeRequest request = new CreateChallengeRequest();
        ReflectionTestUtils.setField(request, "participationFee", 1500);

        Challenge challenge = mock(Challenge.class);
        given(challenge.getId()).willReturn(1L);
        given(challengeRepository.save(any(Challenge.class))).willReturn(challenge);
        willThrow(new PointException(PointErrorCode.INSUFFICIENT_BALANCE)).given(challengeUserService).joinChallenge(1L, 1L, true);

        // when + then
        assertThrows(PointException.class, () -> challengeService.createChallenge(request, 1L));
    }

    /*
    시나리오
    - 1번과 2번 유저가 1번 챌린지에 참여 중
    - 챌린지에 쌓인 totalFee는 1000
    - 1번의 달성률은 100, 2번의 달성률은 0
    예상 결과
    - 챌린지의 상태는 FINISHED
    - 1번은 포인트가 1000, 2번은 포인트가 0 (distribute에서 호출 안 돼야 함)
    */
    @Test
    void 챌린지_종료_시_달성자에게_포인트가_분배된다() {
        // given
        User user1 = new User();
        ReflectionTestUtils.setField(user1, "id", 1L);
        given(userRepository.findById(1L)).willReturn(Optional.of(user1));

        Challenge challenge = new Challenge();
        ReflectionTestUtils.setField(challenge, "id", 1L);
        ReflectionTestUtils.setField(challenge, "status", ONGOING);
        ReflectionTestUtils.setField(challenge, "startedAt", LocalDateTime.now());
        ReflectionTestUtils.setField(challenge, "totalFee", 1000);
        ReflectionTestUtils.setField(challenge, "dueAt", LocalDate.now().minusDays(1)); // 어제 마감
        given(challengeRepository.findById(1L)).willReturn(Optional.of(challenge));

        ChallengeUser challengeUser1 = new ChallengeUser(user1, challenge, true);
        given(challengeUserRepository.findByUserAndChallenge(user1, challenge)).willReturn(Optional.of(challengeUser1));

        UserResponse userResponse1 = UserResponse.from(user1);
        List<UserResponse> content = List.of(userResponse1);
        CursorResponse<UserResponse, Long> cursorResponse = new CursorResponse<>(content, null, false);
        given(challengeUserService.getChallengeParticipantList(1L, 1L, 20))
            .willReturn(cursorResponse);

        ChallengeSuccessRateResponse response1 = new ChallengeSuccessRateResponse(100);
        given(challengeHistoryService.getSuccessRate(1L, 1L)).willReturn(response1);

        PointWallet pointWallet1 = new PointWallet(user1);
        given(pointWalletRepository.findById(1L)).willReturn(Optional.of(pointWallet1));

        UpdateChallengeRequest request = new UpdateChallengeRequest();
        ReflectionTestUtils.setField(request, "status", FINISHED);

        // when
        challengeService.updateChallenge(1L, 1L, request);

        // then
        assertThat(challenge.getStatus()).isEqualTo(FINISHED);
        assertThat(pointWallet1.getBalance()).isEqualTo(1000);
    }
}