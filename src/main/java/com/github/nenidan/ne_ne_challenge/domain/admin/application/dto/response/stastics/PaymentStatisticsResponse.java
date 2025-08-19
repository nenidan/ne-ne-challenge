package com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.stastics;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.model.StatisticDataModel;
import lombok.Getter;

@Getter
public class PaymentStatisticsResponse extends StatisticsResponse{
    private int count;           // 결제 건수
    private double avgAmount;   // 금액 평균

    public PaymentStatisticsResponse(String type, LocalDateTime createdAt, int count, double avgAmount) {
        super(type, createdAt);
        this.count = count;
        this.avgAmount = avgAmount;
    }

    public static PaymentStatisticsResponse fromModel(StatisticDataModel model) {
        if (model == null) return null;
        var p = readPayload(model.getPayload(), PaymentPayload.class);
        return new PaymentStatisticsResponse(
                model.getType().name(),
                model.getCreatedAt(),
                p.count == null ? 0 : p.count,
                p.avgAmount == null ? 0.0 : p.avgAmount
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
            throw new IllegalArgumentException("Invalid payment payload", e);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class PaymentPayload {
        public Integer count;
        public Double avgAmount;
        public PaymentPayload() {}
    }
}
