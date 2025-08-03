package com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.model.AopLogModel;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
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

    public static AopLogResponse fromModel(AopLogModel model) {
        return new AopLogResponse(
                model.getType().name(),
                model.getCreatedAt().atStartOfDay(),
                model.getMethod(),
                model.getParams(),
                model.getResult()
        );
    }

}
