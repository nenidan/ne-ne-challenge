package com.github.nenidan.ne_ne_challenge.domain.user.application.mapper;

import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.JoinCommand;
import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.UpdateProfileCommand;
import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.UserResult;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.Account;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.Profile;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.User;

public class UserMapper {

    private UserMapper() {}

    public static User toDomain(JoinCommand dto) {
        return new User(
            Account.of(
                    dto.getEmail(),
                    dto.getPassword()
            ),
            Profile.of(
                    dto.getNickname(),
                    dto.getBirth(),
                    dto.getBio()
            )
        );
    }

    public static Profile toDomain(UpdateProfileCommand dto) {
        return Profile.of(
                dto.getNickname(),
                dto.getBirth(),
                dto.getBio()
        );
    }

    public static UserResult toDto(User user) {
        return new UserResult(
                user.getId().getValue(),
                user.getAccount().getEmail(),
                user.getAccount().getRole().name(),
                user.getProfile().getNickname(),
                user.getProfile().getBirth().toString(),
                user.getProfile().getBio(),
                user.getAccount().getAuditInfo().getCreatedAt(),
                user.getAccount().getAuditInfo().getUpdatedAt(),
                user.getAccount().getAuditInfo().getDeletedAt()
        );
    }
}
