package com.artlable.backend.login.command.application.dto.login;

import lombok.Getter;


@Getter
public class LoginRequestDTO {

    private String memberEmail;
    private String memberPwd;

}
