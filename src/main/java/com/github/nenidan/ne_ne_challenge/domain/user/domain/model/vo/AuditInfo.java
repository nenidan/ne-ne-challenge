package com.github.nenidan.ne_ne_challenge.domain.user.domain.model.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuditInfo {
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime deletedAt;

    public boolean isDeleted() {
        return deletedAt != null;
    }
}
