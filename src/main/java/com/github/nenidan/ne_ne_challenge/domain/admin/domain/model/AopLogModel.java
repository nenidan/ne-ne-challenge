package com.github.nenidan.ne_ne_challenge.domain.admin.domain.model;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class AopLogModel {
    private final Long id;
    private final DomainType type;
    private final String message;
    private final String userId;
    private final String method;
    private final String params;
    private final String result;
    private final LocalDate createdAt;

    public static AopLogModel of(Long id, DomainType type, String message, String userId, String method, String params, String result, LocalDate createdAt) {
        return new AopLogModel(id, type, message, userId, method, params, result, createdAt);
    }
}
