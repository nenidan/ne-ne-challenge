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
    private Role role;
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

    public static Account of(String email, SocialAccount socialAccount) {
        return new Account(
                null,
                email,
                null,
                Role.USER,
                socialAccount,
                null
        );
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void delete() {
        auditInfo.delete();
    }

    public void updateRole(Role newRole) {
        this.role = newRole;
    }
}
