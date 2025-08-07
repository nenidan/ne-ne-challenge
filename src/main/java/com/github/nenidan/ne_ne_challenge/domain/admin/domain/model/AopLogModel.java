package com.github.nenidan.ne_ne_challenge.domain.admin.domain.model;

import java.time.LocalDate;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AopLogModel {
    private final Long id;
    private final DomainType type;
    private final String userId;
    private final String method;
    private final String params;
    private final String result;
    private final LocalDate createdAt;

    public static AopLogModel of(Long id, DomainType type,  String userId, String method, String params, String result, LocalDate createdAt) {
        return new AopLogModel(id, type, userId, method, params, result, createdAt);
    }
}
