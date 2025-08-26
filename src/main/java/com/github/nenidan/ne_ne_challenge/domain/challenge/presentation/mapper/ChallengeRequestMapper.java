package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.mapper;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.dto.CreateChallengeCommand;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.dto.CreateHistoryCommand;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.dto.UpdateChallengeInfoCommand;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.request.ChallengeSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.request.HistorySearchCond;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.request.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChallengeRequestMapper {
    ChallengeRequestMapper INSTANCE = Mappers.getMapper(ChallengeRequestMapper.class);

    CreateChallengeCommand toCommand(CreateChallengeRequest request);
    UpdateChallengeInfoCommand toCommand(UpdateChallengeInfoRequest request);
    CreateHistoryCommand toCommand(CreateHistoryRequest request);
    ChallengeSearchCond toCond(ChallengeSearchRequest request);
    HistorySearchCond toCond(HistorySearchRequest request);
}
