package com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.stastics;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.model.StatisticDataModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Getter
public class UserStatisticsResponse extends StatisticsResponse{
    private Long newUsersCount;
    private Long totalUsersCount;

    public UserStatisticsResponse(String type, LocalDateTime createdAt, Long newUsersCount, Long totalUsersCount) {
        super(type, createdAt);
        this.newUsersCount = newUsersCount;
        this.totalUsersCount = totalUsersCount;
    }

    public static UserStatisticsResponse fromModel(StatisticDataModel model) {
        if (model == null) return null;
        var p = readPayload(model.getPayload(), UserPayload.class);
        return new UserStatisticsResponse(
                model.getType().name(),
                model.getCreatedAt(),
                p.newUsersCount == null ? 0L : p.newUsersCount,
                p.totalUsersCount == null ? 0L : p.totalUsersCount
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
            throw new IllegalArgumentException("Invalid user payload", e);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class UserPayload {
        public Long newUsersCount;
        public Long totalUsersCount;
        public UserPayload() {}
    }
}
