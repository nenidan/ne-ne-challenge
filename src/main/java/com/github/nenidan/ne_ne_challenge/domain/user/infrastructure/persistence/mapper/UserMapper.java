package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.mapper;

import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.Profile;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.User;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.UserId;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.vo.AuditInfo;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.vo.SocialAccount;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.type.Sex;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.AccountEntity;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.ProfileEntity;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.type.Role;

public class UserMapper {

    private UserMapper() {}

    public static User toDomain(ProfileEntity profileEntity) {
        return new User(
                AccountMapper.toDomain(profileEntity.getAccount()),
                new Profile(
                        new UserId(profileEntity.getId()),
                        profileEntity.getNickname(),
                        profileEntity.getBirth(),
                        Sex.of(profileEntity.getSex().name()),
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

        SocialAccount socialAccount = domain.getAccount().getSocialAccount();

        AuditInfo accountAudit = domain.getAccount().getAuditInfo();
        AuditInfo profileAudit = domain.getProfile().getAuditInfo();

        return new ProfileEntity(
            new AccountEntity(
                domain.getId() != null ? domain.getId().getValue() : null,
                domain.getAccount().getEmail(),
                domain.getAccount().getPassword(),
                Role.of(domain.getAccount().getRole().name()),
                socialAccount != null ? socialAccount.getKakaoId() : null,
                socialAccount != null ? socialAccount.getNaverId() : null,
                socialAccount != null ? socialAccount.getGoogleId() : null,
                accountAudit != null ? accountAudit.getCreatedAt() : null,
                accountAudit != null ? accountAudit.getUpdatedAt() : null,
                accountAudit != null ? accountAudit.getDeletedAt() : null
            ),
            domain.getId() != null ? domain.getId().getValue() : null,
            domain.getProfile().getNickname(),
            domain.getProfile().getBirth(),
            domain.getProfile().getSex().name(),
            domain.getProfile().getBio(),
            profileAudit != null ? profileAudit.getDeletedAt() : null,
            profileAudit != null ? profileAudit.getUpdatedAt() : null,
            profileAudit != null ? profileAudit.getDeletedAt() : null
        );
    }
}
