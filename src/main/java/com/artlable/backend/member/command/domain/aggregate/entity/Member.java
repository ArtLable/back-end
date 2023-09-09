package com.artlable.backend.member.command.domain.aggregate.entity;

import com.artlable.backend.common.AuditingFields;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;

    @Column(length = 100, nullable = false, unique = true)
    private String memberEmail;

    @Column(name = "member_pwd", length = 20)
    private String memberPwd;

//    @Column(nullable = true name = "uid")
//    private String UID; //소셜로그인용

    @Column(length = 300)
    private String memberImage;

    @Column(length = 20)
    private String memberNickname;

}
