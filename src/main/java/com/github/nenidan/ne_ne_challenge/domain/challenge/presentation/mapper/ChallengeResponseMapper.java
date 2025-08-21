package com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.mapper;

import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.response.ChallengeDto;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.response.ChallengeHistoryDto;
import com.github.nenidan.ne_ne_challenge.domain.challenge.application.query.dto.response.ChallengeSuccessRateDto;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.response.ChallengeHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.response.ChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.presentation.dto.response.ChallengeSuccessRateResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ChallengeResponseMapper {
    ChallengeResponseMapper INSTANCE = Mappers.getMapper(ChallengeResponseMapper.class);

    ChallengeResponse fromDto(ChallengeDto dto);

    @Mapping(source = "success", target = "isSuccess")
    ChallengeHistoryResponse fromDto(ChallengeHistoryDto dto);
    ChallengeSuccessRateResponse fromDto(ChallengeSuccessRateDto dto);

    default CursorResponse<ChallengeResponse, LocalDateTime> fromChallengeCursorDto(
        CursorResponse<ChallengeDto, LocalDateTime> dtoResponse) {
        List<ChallengeResponse> content = dtoResponse.getContent().stream()
            .map(this::fromDto)
            .collect(Collectors.toList());

        return new CursorResponse<>(content, dtoResponse.getNextCursor(), dtoResponse.isHasNext());
    }

    default CursorResponse<ChallengeHistoryResponse, LocalDateTime> fromChallengeHistoryCursorDto(
        CursorResponse<ChallengeHistoryDto, LocalDateTime> dtoResponse) {
        List<ChallengeHistoryResponse> content = dtoResponse.getContent().stream()
            .map(this::fromDto)
            .collect(Collectors.toList());

        return new CursorResponse<>(content, dtoResponse.getNextCursor(), dtoResponse.isHasNext());
    }
}
