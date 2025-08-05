package com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.model.AopLogModel;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AopLogResponse extends LogsResponse {
    private String method;
    private String params;
    private String result;
    private Boolean success;
    private long targetId;

    public AopLogResponse(String type, LocalDateTime createdAt, String method, String params, String result, Boolean success, Long targetId) {
        super(type, createdAt);
        this.method = method;
        this.params = params;
        this.result = result;
        this.success = success;
        this.targetId = targetId;
    }

    public static AopLogResponse fromModel(AopLogModel model) {
        return new AopLogResponse(
                model.getType().name(),
                model.getCreatedAt().atStartOfDay(),
                model.getMethod(),
                model.getParams(),
                model.getResult(),
                model.getSuccess(),
                model.getTargetId()
        );
    }

}
