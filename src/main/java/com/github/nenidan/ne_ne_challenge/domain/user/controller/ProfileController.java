package com.github.nenidan.ne_ne_challenge.domain.user.controller;

import com.github.nenidan.ne_ne_challenge.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProfileController {

    private final UserService userService;


}
