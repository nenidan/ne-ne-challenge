package com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.inner.InnerChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.inner.InnerHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.inner.InnerParticipantResponse;

import java.util.List;

public interface InnerChallengeQueryRepository {

    List<InnerChallengeResponse> findAllChallenges();

    List<InnerParticipantResponse> findAllParticipants();

    List<InnerHistoryResponse> findAllHistory();
}
