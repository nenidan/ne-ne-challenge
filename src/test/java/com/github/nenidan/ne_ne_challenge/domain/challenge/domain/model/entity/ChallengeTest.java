package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.dto.ChallengeRequestInfo;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeException;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ChallengeTest {

    @Test
    void 참가비가_0인_챌린지는_생성할_수_없다() {
        // given
        ChallengeRequestInfo requestInfo = new ChallengeRequestInfo();
        requestInfo.setName("name");
        requestInfo.setCategory(ChallengeCategory.STUDY);
        requestInfo.setDescription("description");
        requestInfo.setDueAt(LocalDate.now().plusDays(1));
        requestInfo.setStartAt(LocalDate.now());
        requestInfo.setMaxParticipants(4);
        requestInfo.setMinParticipants(4);
        requestInfo.setParticipationFee(0);

        // when + then
        assertThrows(ChallengeException.class,
            () -> Challenge.createChallenge(1L, 1, requestInfo));
    }
}