package com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.mapper;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.dto.UpdateChallengeInfoCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.dto.CreateChallengeCommand;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.dto.ChallengeRequestInfo;

@Mapper
public interface ChallengeMapper {
    ChallengeMapper INSTANCE = Mappers.getMapper(ChallengeMapper.class);

    ChallengeRequestInfo toInfo(CreateChallengeCommand command);
    ChallengeRequestInfo toInfo(UpdateChallengeInfoCommand command);
}
