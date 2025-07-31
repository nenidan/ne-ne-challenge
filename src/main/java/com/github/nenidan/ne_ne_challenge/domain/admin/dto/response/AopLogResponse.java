package com.github.nenidan.ne_ne_challenge.domain.admin.dto.response;

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

}
