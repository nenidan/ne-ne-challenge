package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.search.mapper;

import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.UserResult;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.User;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.search.document.UserDocument;

public class UserMapper {

    UserMapper() {}

    public static UserDocument toDocument(User user) {
        return new UserDocument(
                user.getId().getValue().toString(),
                user.getAccount().getEmail(),
                user.getAccount().getRole().toString(),
                user.getProfile().getNickname(),
                user.getProfile().getBirth().getYear(),
                user.getProfile().getBirth().toString().substring(5),
                user.getProfile().getSex().toString(),
                user.getProfile().getBio(),
                user.getAccount().getAuditInfo().getCreatedAt().toString(),
                user.getAccount().getAuditInfo().getUpdatedAt().toString(),
                user.getAccount().getAuditInfo().getDeletedAt() != null ? user.getAccount().getAuditInfo().getDeletedAt().toString() : null
        );
    }

    public static UserResult toDto(UserDocument document) {
        return new UserResult(
                Long.parseLong(document.getId()),
                document.getEmail(),
                document.getRole(),
                document.getNickname(),
                String.format("%04d-%s", document.getBirthYear(), document.getBirthDay()),
                document.getBio(),
                LocalDateTime.parse(document.getCreatedAt()),
                LocalDateTime.parse(document.getUpdatedAt()),
                document.getDeletedAt() != null ? LocalDateTime.parse(document.getDeletedAt()) : null
        );
    }


}
