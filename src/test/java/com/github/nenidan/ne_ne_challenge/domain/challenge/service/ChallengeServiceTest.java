package com.github.nenidan.ne_ne_challenge.domain.challenge.service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.CreateChallengeRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.repository.ChallengeRepository;
import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointException;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import com.github.nenidan.ne_ne_challenge.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ChallengeServiceTest {

    @Mock
    UserRepository userRepository;

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
}