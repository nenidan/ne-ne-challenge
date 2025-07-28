package com.github.nenidan.ne_ne_challenge.global.dto;

import java.time.LocalDateTime;

import lombok.Getter;

// 각 응답 객체에서 @AllArgsConstructor 대신 생성자 직접 생성 필요
@Getter
public abstract class InnerResponseBase {

    protected LocalDateTime createdAt;

    protected LocalDateTime updatedAt;

    protected LocalDateTime deletedAt;

    public InnerResponseBase(LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
}
