package com.artlable.backend.login.command.application.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags= "Login API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LoginController {
}
