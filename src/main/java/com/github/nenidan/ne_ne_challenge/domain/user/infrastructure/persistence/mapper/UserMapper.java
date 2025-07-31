package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.mapper;

import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.Profile;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.User;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.UserId;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.AccountEntity;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.ProfileEntity;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.vo.AuditInfo;

public class UserMapper {

    private UserMapper() {}

    public static User toDomain(ProfileEntity profileEntity) {

        return new User(
                AccountMapper.toDomain(profileEntity.getAccount()),
                new Profile(
                        new UserId(profileEntity.getId()),
                        profileEntity.getNickname(),
                        profileEntity.getBirth(),
                        profileEntity.getBio(),
                        profileEntity.getImageId(),
                        new AuditInfo(
                                profileEntity.getCreatedAt(),
                                profileEntity.getUpdatedAt(),
                                profileEntity.getDeletedAt()
                        )
                )
        );
    }

    public static ProfileEntity toEntity(User domain) {

        return ProfileEntity.of(
                AccountEntity.of(
                        domain.getId() != null ? domain.getId().getValue() : null,
                        domain.getAccount().getEmail(),
                        domain.getAccount().getPassword()
                ),
                domain.getProfile().getNickname(),
                domain.getProfile().getBirth(),
                domain.getProfile().getBio()
        );
    }
}
