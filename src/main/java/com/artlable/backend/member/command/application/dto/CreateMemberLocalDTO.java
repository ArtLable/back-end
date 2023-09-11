package com.artlable.backend.member.command.application.dto;

import lombok.Getter;

import javax.validation.constraints.*;

@Getter
public class CreateMemberLocalDTO {

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private final String memberEmail;

    @NotBlank(message = "패스워드를 입력해주세요.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private final String memberPwd;

    private final String memberRole;

    private final String memberNickname;

    @NotBlank(message = "성별을 입력해 주세요.")
    private final String memberGender;

    @NotBlank(message = "연령대를 입력해 주세요.")
    private final String memberAgeRange;

    public CreateMemberLocalDTO(String memberEmail, String memberPwd, String memberNickname, String memberGender, String memberAgeRange) {
        this.memberEmail = memberEmail;
        this.memberPwd = memberPwd;
        this.memberRole = "MEMBER";
        this.memberNickname = memberNickname;
        this.memberGender = memberGender;
        this.memberAgeRange = memberAgeRange;
    }
}
