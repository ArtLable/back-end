package com.artlable.backend.member.command.domain.aggregate.entity;

import com.artlable.backend.common.AuditingFields;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class MemberEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "member_no")
    private Long memberNo;

    @Column(name = "member_id", length = 20)
    private String memberId;

    @Column(name = "member_pwd", length = 20)
    private String memberPwd;

    @Column(name = "member_nickname", length = 20)
    private String memberNickname;

    @Column(name = "member_email")
    private String memberEmail;

    public MemberEntity(Long memberNo, String memberId, String memberPwd, String memberNickname, String memberEmail) {
        this.memberNo = memberNo;
        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.memberNickname = memberNickname;
        this.memberEmail = memberEmail;
    }

    public void setMemberNo(Long memberNo) {
        this.memberNo = memberNo;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setMemberPwd(String memberPwd) {
        this.memberPwd = memberPwd;
    }

    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }
}
