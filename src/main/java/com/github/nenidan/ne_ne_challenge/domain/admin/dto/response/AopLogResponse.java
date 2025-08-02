package com.github.nenidan.ne_ne_challenge.domain.admin.dto.response;

import com.github.nenidan.ne_ne_challenge.domain.admin.entity.AopLog;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AopLogResponse extends LogsResponse {
    private String method;
    private String params;
    private String result;

    public AopLogResponse(String type, LocalDateTime createdAt, String method, String params, String result) {
        super(type, createdAt);
        this.method = method;
        this.params = params;
        this.result = result;
    }

    public static AopLogResponse fromEntity(AopLog log) {
        return new AopLogResponse(
                log.getType().name(),
                log.getCreatedAt(),
                log.getMethod(),
                log.getParams(),
                log.getResult()
        );
    }

}
