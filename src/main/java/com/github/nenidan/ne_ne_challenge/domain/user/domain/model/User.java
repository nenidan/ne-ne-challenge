package com.github.nenidan.ne_ne_challenge.domain.user.domain.model;

import lombok.Getter;

@Getter
public class User {
    private final UserId id;
    private final Account account;
    private final Profile profile;

    public User(Account account, Profile profile) {
        this.id = account.getId();
        this.account = account;
        this.profile = profile;
    }

    public void updatePassword(String password) {
        account.updatePassword(password);
    }

    public void updateProfile(Profile profile) {
        this.profile.updateProfile(profile);
    }
}
