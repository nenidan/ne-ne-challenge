package com.github.nenidan.ne_ne_challenge.domain.challenge.application.command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateHistoryCommand {

    private String content;
}
