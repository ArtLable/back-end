package com.artlable.backend.member.command.domain.aggregate.entity;

import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.member.command.domain.aggregate.entity.enumvalue.MemberRole;
import com.artlable.backend.member.command.domain.aggregate.entity.enumvalue.MemberSocialLogin;
import com.artlable.backend.member.command.domain.aggregate.vo.MemberVO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;

    @Column(length = 100, nullable = false, unique = true)
    private String memberEmail;

    @Column(length = 200)
    private String memberPwd;

    @Column(length = 500)
    private String memberImage;
    //권한
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole memberRole;

    @Embedded
    private MemberVO memberVO;

    //소셜 로그인 테이블 조인
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Auth> auth;

    //일반 로그인
    public Member(String memberEmail, String memberPwd, MemberRole memberRole, MemberVO memberVO) {
        this.memberEmail = memberEmail;
        this.memberPwd = memberPwd;
        this.memberRole = memberRole;
        this.memberVO = memberVO;
    }

    // 소셜 로그인
    public Member(String memberEmail, String memberImage, MemberRole memberRole, MemberVO memberVO, List<Auth> auth) {
        this.memberEmail = memberEmail;
        this.memberImage = memberImage;
        this.memberRole = memberRole;
        this.memberVO = memberVO;
        this.auth = auth;

    }
}
