package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.mapper;

import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.Account;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.UserId;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.vo.AuditInfo;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.vo.SocialAccount;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.type.Role;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.AccountEntity;

public class AccountMapper {

    private AccountMapper() {}

    public static Account toDomain(AccountEntity entity) {
        return new Account(
                new UserId(entity.getId()),
                entity.getEmail(),
                entity.getPassword(),
                Role.of(entity.getRole().name()),
                new SocialAccount(
                        entity.getKakaoId(),
                        entity.getNaverId(),
                        entity.getGoogleId()
                ),
                new AuditInfo(
                        entity.getCreatedAt(),
                        entity.getUpdatedAt(),
                        entity.getDeletedAt()
                )
        );
    }
}
