package com.artlable.backend.member.command.domain.aggregate.entity;

import com.artlable.backend.common.AuditingFields;
import com.artlable.backend.feed.command.domain.aggregate.entity.Feed;
import com.artlable.backend.member.command.domain.aggregate.entity.enumvalue.MemberRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo; //회원관리번호

    @Column(length = 100, nullable = false, unique = true)
    private String memberEmail; // 로그인 아이디

    @Column(length = 200)
    private String memberPwd; //로그인 비밀번호

    @Column(nullable = false)
    private String isDeleted; // 활성화 상태

    @Column(length = 300)
    private String memberImage; // 프로필 사진
    //권한
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole memberRole;

    @Column
    private String memberNickname;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Feed> feedLists;

//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
//    private List<Comment> commentLists;
//
//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
//    private List<Like> likeList;
//
//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
//    private List<File> fileList;

    //소셜 로그인 테이블 조인
    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Authority> authority;

    @Builder
    public Member(Long memberNo, String memberEmail, String memberPwd, String isDeleted, String memberImage,
                  String memberNickname, MemberRole memberRole, List<Feed> feedLists, List<Authority> authority){
        this.memberNo = memberNo;
        this.memberEmail = memberEmail;
        this.memberPwd = memberPwd;
        this.isDeleted = isDeleted;
        this.memberImage = memberImage;
        this.memberNickname = memberNickname;
        this.memberRole = memberRole;
        this.feedLists = feedLists;
        this.authority = authority;
    }

    //비밀번호 변경
    public void setMemberPwd(String memberPwd) {
        this.memberPwd = memberPwd;
    }

    //닉네임 변경
    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }

    //프로필사진 변경
    public void setMemberImage(String memberImage) {
        this.memberImage = memberImage;
    }
}
