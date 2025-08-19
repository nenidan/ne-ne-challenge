package com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.stastics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.model.StatisticDataModel;
import lombok.Getter;

@Getter
public class ChallengeStatisticsResponse extends StatisticsResponse{

    private Map<LocalDate, Long> dailyParticipants;   // 일별 참가자 수
    private Map<YearMonth, Long> monthlyParticipants; // 월별 참가자 수
    private double participationRate;                 // 챌린지 참여율 (시작된 챌린지 / 전체 챌린지)

    public ChallengeStatisticsResponse(String type, LocalDateTime createdAt, Map<LocalDate, Long> dailyParticipants,
                                       Map<YearMonth, Long> monthlyParticipants,
                                       double participationRate) {
        super(type, createdAt);
        this.dailyParticipants = dailyParticipants; 
        this.monthlyParticipants = monthlyParticipants;
        this.participationRate = participationRate;
    }

    public static ChallengeStatisticsResponse fromModel(StatisticDataModel model) {
        if (model == null) return null;
        var payloadJson = model.getPayload();

        ChallengePayload p = readPayload(payloadJson, ChallengePayload.class);
        Map<LocalDate, Long> daily = p.dailyParticipants == null ? Map.of()
                : p.dailyParticipants.entrySet().stream()
                .collect(Collectors.toMap(e -> LocalDate.parse(e.getKey()), Map.Entry::getValue));

        Map<YearMonth, Long> monthly = p.monthlyParticipants == null ? Map.of()
                : p.monthlyParticipants.entrySet().stream()
                .collect(Collectors.toMap(e -> YearMonth.parse(e.getKey()), Map.Entry::getValue));

        return new ChallengeStatisticsResponse(
                model.getType().name(),
                model.getCreatedAt(),
                daily,
                monthly,
                p.participationRate == null ? 0.0 : p.participationRate
        );
    }

    // --- 내부 구현부 ---

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

    private static <T> T readPayload(String json, Class<T> type) {
        try {
            if (json == null || json.isBlank()) return type.getDeclaredConstructor().newInstance();
            return MAPPER.readValue(json, type);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid challenge payload", e);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ChallengePayload {
        // JSON 저장 시 키는 문자열(예: "2025-08-01", "2025-08")로 넣어두고, 읽을 때 변환
        public Map<String, Long> dailyParticipants;
        public Map<String, Long> monthlyParticipants;
        public Double participationRate;

        public ChallengePayload() {}
    }

}
