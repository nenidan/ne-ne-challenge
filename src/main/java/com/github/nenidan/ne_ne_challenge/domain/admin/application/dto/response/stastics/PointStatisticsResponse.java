package com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.stastics;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.model.StatisticDataModel;
import lombok.Getter;

@Getter
public class PointStatisticsResponse extends StatisticsResponse {
    private double ReasonRate;
    private int cnt;
    private String reason;

    public PointStatisticsResponse(String type, LocalDateTime createdAt, double ReasonRate, int cnt, String reason) {
        super(type, createdAt);
        this.ReasonRate = ReasonRate;
        this.cnt = cnt;
        this.reason = reason;
    }

    public static PointStatisticsResponse fromModel(StatisticDataModel model) {
        if (model == null) return null;
        var p = readPayload(model.getPayload(), PointPayload.class);
        return new PointStatisticsResponse(
                model.getType().name(),
                model.getCreatedAt(),
                p.reasonRate == null ? 0.0 : p.reasonRate,
                p.cnt == null ? 0 : p.cnt,
                p.reason
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
            throw new IllegalArgumentException("Invalid point payload", e);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class PointPayload {
        // 과거 대문자 "ReasonRate"로 저장되었을 가능성 대비
        @JsonAlias({"reasonRate", "ReasonRate"})
        public Double reasonRate;
        public Integer cnt;
        public String reason;
        public PointPayload() {}
    }
}
