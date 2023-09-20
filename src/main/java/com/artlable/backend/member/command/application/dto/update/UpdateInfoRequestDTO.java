package com.artlable.backend.member.command.application.dto.update;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
public class UpdateInfoRequestDTO {

    @NotEmpty(message = "패스워드를 입력해주세요.")
    private String currentPassword; //변경할 패스워드

    @NotBlank(message = "패스워드를 입력해주세요.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String memberPwd; //기존패스워드

    @NotBlank
    @Pattern(regexp ="^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,16}$",
            message = "닉네임은 2자이상 16자이하, 영어 또는 숫자 또는 한글로 구성해야 합니다.")
    private String memberNickname;

}
