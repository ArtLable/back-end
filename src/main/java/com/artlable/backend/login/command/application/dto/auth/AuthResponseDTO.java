package com.artlable.backend.login.command.application.dto.auth;

import com.artlable.backend.member.command.domain.aggregate.entity.enumvalue.MemberSocialLogin;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter // 모델매퍼 이용
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthResponseDTO {
    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private MemberSocialLogin memberSocialLogin;

    public AuthResponseDTO(String tokenType, String accessToken, String refreshToken, MemberSocialLogin memberSocialLogin){
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.refreshToken= refreshToken;
        this.memberSocialLogin = memberSocialLogin;
    }
}
