package com.artlable.backend.member.command.application.dto.auth;

import lombok.Getter;

@Getter
public class AuthRequestDTO {

    private String memberEmail;
    private String memberPwd;
}
