package com.github.nenidan.ne_ne_challenge.domain.user.domain.model;

import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.vo.AuditInfo;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.vo.SocialAccount;
import com.github.nenidan.ne_ne_challenge.domain.user.domain.type.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Account {
    private final UserId id;
    private final String email;
    private String password;
    private final Role role;
    private final SocialAccount socialAccount;
    private final AuditInfo auditInfo;

    public static Account of(String email, String password) {
        return new Account(
                null,
                email,
                password,
                Role.USER,
                null,
                null
        );
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
