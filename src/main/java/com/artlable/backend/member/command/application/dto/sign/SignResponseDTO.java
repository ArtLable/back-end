package com.artlable.backend.member.command.application.dto.sign;

import com.artlable.backend.member.command.domain.aggregate.entity.Authority;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.member.command.domain.aggregate.entity.enumvalue.MemberRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignResponseDTO {

    private Long memberNo;
    private String memberEmail;
    private String isDeleted;
    private String memberImage;
    private MemberRole memberRole;
    private List<Authority> auth;

    @Builder
    public SignResponseDTO(Member member){
        this.memberNo = member.getMemberNo();
        this.memberEmail = member.getMemberEmail();
        this.isDeleted = member.getIsDeleted();
        this.memberImage = member.getMemberImage();
        this.memberRole = member.getMemberRole();
        this.auth = member.getAuth();
    }
}
