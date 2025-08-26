package com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserIdListRequest {

    @NotNull
    private List<Long> userIdList;
}
