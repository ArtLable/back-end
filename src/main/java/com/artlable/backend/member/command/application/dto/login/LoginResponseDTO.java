package com.artlable.backend.member.command.application.dto.login;

import com.artlable.backend.member.command.application.dto.auth.AuthResponseDTO;
import com.artlable.backend.member.command.domain.aggregate.entity.enumvalue.MemberRole;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDTO {

    private Long memberNo;
    private String memberNickname;
    private String memberImage;
    private MemberRole memberRole;
    private List<AuthResponseDTO> authority;


    //로그인 응답
    @Builder
    public LoginResponseDTO(Long memberNo, String memberNickname, String memberImage, MemberRole memberRole, List<AuthResponseDTO> authority) {
        this.memberNo = memberNo;
        this.memberNickname = memberNickname;
        this.memberImage = memberImage;
        this.memberRole = memberRole;
        this.authority = authority;
    }
}
