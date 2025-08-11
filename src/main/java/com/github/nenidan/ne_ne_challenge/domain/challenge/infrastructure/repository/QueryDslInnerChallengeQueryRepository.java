package com.github.nenidan.ne_ne_challenge.domain.challenge.infrastructure.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.inner.InnerChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.inner.InnerHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.inner.InnerParticipantResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.repository.InnerChallengeQueryRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.QChallenge.challenge;
import static com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.QHistory.history;
import static com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.QParticipant.participant;

@Repository
@RequiredArgsConstructor
public class QueryDslInnerChallengeQueryRepository implements InnerChallengeQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<InnerChallengeResponse> findAllChallenges() {
        return queryFactory.select(Projections.constructor(InnerChallengeResponse.class,
            challenge.id,
            challenge.name,
            challenge.description,
            challenge.status,
            challenge.category,
            challenge.hostId,
            challenge.minParticipants,
            challenge.maxParticipants,
            challenge.currentParticipantCount,
            challenge.participationFee,
            challenge.totalFee,
            challenge.startAt,
            challenge.dueAt,
            challenge.createdAt,
            challenge.updatedAt,
            challenge.deletedAt
        ))
        .from(challenge)
        .fetch();
    }

    @Override
    public List<InnerParticipantResponse> findAllParticipants() {
        return queryFactory.select(Projections.constructor(InnerParticipantResponse.class,
                participant.id,
                participant.userId,
                participant.challengeId,
                participant.createdAt,
                participant.updatedAt,
                participant.deletedAt
            ))
            .from(participant)
            .fetch();
    }

    @Override
    public List<InnerHistoryResponse> findAllHistory() {
        return queryFactory.select(Projections.constructor(InnerHistoryResponse.class,
            history.id,
            history.userId,
            history.challengeId,
            history.content,
            history.isSuccess,
            history.date,
            history.createdAt,
            history.updatedAt,
            history.deletedAt
            ))
            .from(history)
            .fetch();
    }
}
