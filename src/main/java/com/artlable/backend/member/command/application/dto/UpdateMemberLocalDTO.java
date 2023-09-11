package com.artlable.backend.member.command.application.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class UpdateMemberLocalDTO {

    @NotBlank(message = "패스워드를 입력해주세요.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private  String memberPwd;

    private  String memberRole;

    private String memberNickName;

    @NotBlank(message = "성별을 입력해 주세요.")
    private  String memberGender;

    @NotBlank(message = "연령대를 입력해 주세요.")
    private  String memberAgeRange;

    public UpdateMemberLocalDTO(String memberPwd, String memberRole, String memberNickName, String memberGender, String memberAgeRange) {
        this.memberPwd = memberPwd;
        this.memberRole = memberRole;
        this.memberNickName = memberNickName;
        this.memberGender = memberGender;
        this.memberAgeRange = memberAgeRange;
    }

    public UpdateMemberLocalDTO setMemberPwd(String memberPwd){
        this.memberPwd = memberPwd;
        return this;
    }

    public UpdateMemberLocalDTO setMemberRole(String memberRole){
        this.memberRole = memberRole;
        return this;
    }

    public UpdateMemberLocalDTO setMemberNickname(String memberNickName){
        this.memberNickName = memberNickName;
        return this;
    }

    public UpdateMemberLocalDTO setMemberGender(String memberGender){
        this.memberGender = memberGender;
        return this;
    }

    public UpdateMemberLocalDTO setMemberAgeRange(String memberAgeRange){
        this.memberAgeRange = memberAgeRange;
        return this;
    }
}
