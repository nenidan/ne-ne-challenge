package com.github.nenidan.ne_ne_challenge.domain.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.nenidan.ne_ne_challenge.domain.admin.dto.response.stastics.ChallengeStatisticsResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"  // optional: 직렬화 시 type 필드 포함됨
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ChallengeStatisticsResponse.class, name = "challenge")
})
@NoArgsConstructor
@Getter
public abstract class StatisticsResponse {
    private String type;
    private LocalDateTime createdAt;

    public StatisticsResponse(String type, LocalDateTime createdAt) {
        this.type = type;
        this.createdAt = createdAt;
    }
}
