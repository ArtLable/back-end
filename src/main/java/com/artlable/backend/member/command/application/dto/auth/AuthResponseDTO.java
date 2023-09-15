package com.artlable.backend.member.command.application.dto.auth;

import com.artlable.backend.member.command.domain.aggregate.entity.Authority;
import com.artlable.backend.member.command.domain.aggregate.entity.enumvalue.MemberSocialLogin;
import lombok.Getter;

@Getter
public class AuthResponseDTO {
    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private MemberSocialLogin memberSocialLogin;

    public AuthResponseDTO(Authority auth){
        this.tokenType = auth.getTokenType();
        this.accessToken = auth.getAccessToken();
        this.refreshToken= auth.getRefreshToken();
        this.memberSocialLogin = auth.getMemberSocialLogin();
    }
}
