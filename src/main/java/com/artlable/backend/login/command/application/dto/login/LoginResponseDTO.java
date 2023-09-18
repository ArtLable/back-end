package com.artlable.backend.login.command.application.dto.login;

import com.artlable.backend.login.command.application.dto.auth.AuthResponseDTO;
import com.artlable.backend.login.command.domain.agreegate.entity.Authority;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.member.command.domain.aggregate.entity.enumvalue.MemberRole;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDTO {

    private String memberEmail;
    private String memberImage;
    private MemberRole memberRole;
    private List<AuthResponseDTO> authority;


    @Builder
    public LoginResponseDTO(String memberEmail, String memberImage, MemberRole memberRole, List<AuthResponseDTO> authority) {
        this.memberEmail = memberEmail;
        this.memberImage = memberImage;
        this.memberRole = memberRole;
        this.authority = authority;
    }
}
