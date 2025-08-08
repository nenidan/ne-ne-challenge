package com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class LogsResponse implements CommonId {

    public LogsResponse(String type, LocalDateTime createdAt) {
    }

    private String type;
    private LocalDateTime createdAt;

    @Setter
    private List<AopLogResponse> logs = new ArrayList<>();

    @Override
    public abstract Long getId();

    public void insertLogs (List<AopLogResponse> aopLogs){
        this.logs = aopLogs;
    }

}
