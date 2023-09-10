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

    @Column(length = 100, nullable = false)
    private String memberName;

    @Enumerated(EnumType.STRING)
    @Column
    private MemberGender memberGender;

    @Enumerated(EnumType.STRING)
    @Column
    private MemberAgeRange memberAgeRange;

    public MemberVO(String memberName, MemberGender memberGender, MemberAgeRange memberAgeRange){
        this.memberName = memberName;
        this.memberGender = memberGender;
        this.memberAgeRange = memberAgeRange;
    }

    public MemberVO setMemberName(String memberName){
        this.memberName = memberName;
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
