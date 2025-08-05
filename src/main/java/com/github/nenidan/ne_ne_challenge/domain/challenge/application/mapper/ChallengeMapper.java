package com.github.nenidan.ne_ne_challenge.domain.challenge.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.request.CreateChallengeRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.request.UpdateChallengeInfoRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.response.ChallengeHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.dto.response.ChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.dto.ChallengeInfo;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.History;

@Mapper
public interface ChallengeMapper {
    ChallengeMapper INSTANCE = Mappers.getMapper(ChallengeMapper.class);

    ChallengeResponse toResponse(Challenge challenge);
    ChallengeInfo toInfo(CreateChallengeRequest request);
    ChallengeInfo toInfo(UpdateChallengeInfoRequest request);

    @Mapping(source = "challenge.id", target = "challengeId")
    ChallengeHistoryResponse toResponse(History history);
}
