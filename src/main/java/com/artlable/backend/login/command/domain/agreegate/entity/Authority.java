package com.artlable.backend.login.command.domain.agreegate.entity;

import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.member.command.domain.aggregate.entity.enumvalue.MemberSocialLogin;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authNo;

    @Column(nullable = false)
    private String tokenType;

    @Column(nullable = false)
    private String accessToken;

    @Column(nullable = false)
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberSocialLogin memberSocialLogin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNo")
    private Member member;

    @Builder
    public Authority(Long authNo, String tokenType, String accessToken, String refreshToken, MemberSocialLogin memberSocialLogin, Member member){
        this.authNo = authNo;
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.memberSocialLogin = memberSocialLogin;
        this.member = member;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setMemberSocialLogin(MemberSocialLogin memberSocialLogin) {
        this.memberSocialLogin = memberSocialLogin;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
