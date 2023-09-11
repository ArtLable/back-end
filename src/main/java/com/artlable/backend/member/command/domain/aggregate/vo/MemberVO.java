package com.artlable.backend.member.command.domain.aggregate.vo;

import com.artlable.backend.member.command.domain.aggregate.entity.enumvalue.MemberAgeRange;
import com.artlable.backend.member.command.domain.aggregate.entity.enumvalue.MemberGender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberVO {

    @Column(length = 20)
    private String memberNickname;

    @Enumerated(EnumType.STRING)
    @Column
    private MemberGender memberGender;

    @Enumerated(EnumType.STRING)
    @Column
    private MemberAgeRange memberAgeRange;

    public MemberVO(String memberNickname, MemberGender memberGender, MemberAgeRange memberAgeRange){
        this.memberNickname = memberNickname;
        this.memberGender = memberGender;
        this.memberAgeRange = memberAgeRange;
    }

    public MemberVO setMemberNickname(String memberNickname){
        this.memberNickname = memberNickname;
        return this;
    }
    public MemberVO setMemberGender(MemberGender memberGender){
        this.memberGender = memberGender;
        return this;
    }

    public MemberVO setMemberAgeRange(MemberAgeRange memberAgeRange){
        this.memberAgeRange = memberAgeRange;
        return this;
    }
}
