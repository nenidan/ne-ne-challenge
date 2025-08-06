package com.github.nenidan.ne_ne_challenge.domain.challenge.infrastructure.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.response.ChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.repository.ChallengeQueryRepository;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.QChallenge;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QueryDslChallengeQueryRepository implements ChallengeQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ChallengeResponse> findChallengeById(Long challengeId) {
        QChallenge challenge = QChallenge.challenge;

        ChallengeResponse result = queryFactory
            .select(Projections.bean(ChallengeResponse.class,
                challenge.id,
                challenge.name,
                challenge.description,
                challenge.status,
                challenge.category,
                challenge.minParticipants,
                challenge.maxParticipants,
                challenge.participationFee,
                challenge.totalFee,
                challenge.dueAt,
                challenge.startAt,
                challenge.createdAt,
                challenge.updatedAt,
                challenge.deletedAt
            ))
            .from(challenge)
            .where(challenge.id.eq(challengeId))
            .fetchOne();

        return Optional.ofNullable(result);
    }
}
