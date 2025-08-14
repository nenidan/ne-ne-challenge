package com.github.nenidan.ne_ne_challenge.domain.user.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDeletedEvent {

    private String id;

    public UserDeletedEvent(Long id) {
        this.id = id.toString();
    }
}
