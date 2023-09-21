package com.artlable.backend.member.command.application.dto.auth;

import com.artlable.backend.member.command.domain.aggregate.entity.enumvalue.MemberSocialLogin;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter // 모델매퍼 이용
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthResponseDTO {

    //로그인응답 (토큰응답)
    private String accessToken;
    private MemberSocialLogin memberSocialLogin;

    public AuthResponseDTO(String accessToken, MemberSocialLogin memberSocialLogin){
        this.accessToken = accessToken;
        this.memberSocialLogin = memberSocialLogin;
    }
}
