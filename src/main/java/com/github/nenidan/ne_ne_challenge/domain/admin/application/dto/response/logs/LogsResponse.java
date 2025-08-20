package com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class LogsResponse implements CommonId {

    @Setter
    private List<AopLogResponse> logs = new ArrayList<>();

    private String type;
    private LocalDateTime createdAt;

    public LogsResponse(String type, LocalDateTime createdAt) {
    }

    @Override
    public abstract Long getId();

    public void insertLogs (List<AopLogResponse> aopLogs){
        this.logs = aopLogs;
    }

}
