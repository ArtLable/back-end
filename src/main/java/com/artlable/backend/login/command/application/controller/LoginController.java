package com.artlable.backend.login.command.application.controller;

import com.artlable.backend.login.command.application.dto.login.LoginRequestDTO;
import com.artlable.backend.login.command.application.dto.login.LoginResponseDTO;
import com.artlable.backend.login.command.application.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags= "Login API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @ApiOperation(value = "로그인 요청")
    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO requestDTO) throws Exception{
        return new ResponseEntity<>(loginService.login(requestDTO), HttpStatus.OK);
    }
}
