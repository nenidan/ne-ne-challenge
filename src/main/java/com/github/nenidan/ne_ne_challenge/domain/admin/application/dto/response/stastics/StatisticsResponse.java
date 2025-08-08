package com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.stastics;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type" // 직렬화 시 포함될 필드명
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PaymentStatisticsResponse.class, name = "payment"),
        @JsonSubTypes.Type(value = PointStatisticsResponse.class, name = "point"),
        @JsonSubTypes.Type(value = ChallengeStatisticsResponse.class, name = "challenge")
})
@AllArgsConstructor
@Getter
public abstract class StatisticsResponse {
    private String type;
    private LocalDateTime createdAt;

}