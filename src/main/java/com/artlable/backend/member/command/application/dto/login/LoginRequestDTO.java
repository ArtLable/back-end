package com.artlable.backend.member.command.application.dto.login;

import lombok.Getter;


@Getter
public class LoginRequestDTO {

    private String memberEmail;
    private String memberPwd;
    private String isDeleted;
    private String memberNickname;

}
