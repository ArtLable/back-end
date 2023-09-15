package com.artlable.backend.member.command.application.dto.login;

import com.artlable.backend.member.command.domain.aggregate.entity.Authority;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.member.command.domain.aggregate.entity.enumvalue.MemberRole;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDTO {

    private Long memberNo;
    private String memberEmail;
    private String memberImage;
    private MemberRole memberRole;
    private List<Authority> auth;

    @Builder
    public LoginResponseDTO(Member member){
        this.memberNo = member.getMemberNo();
        this.memberEmail = member.getMemberEmail();
        this.memberImage = member.getMemberImage();
        this.memberRole = member.getMemberRole();
        this.auth = member.getAuth();
    }

}
