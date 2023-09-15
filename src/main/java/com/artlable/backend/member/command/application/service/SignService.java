package com.artlable.backend.member.command.application.service;

import com.artlable.backend.jwt.TokenProvider;
import com.artlable.backend.member.command.application.dto.login.LoginRequestDTO;
import com.artlable.backend.member.command.application.dto.login.LoginResponseDTO;
import com.artlable.backend.member.command.application.dto.sign.SignRequestDTO;
import com.artlable.backend.member.command.application.dto.sign.SignResponseDTO;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.member.command.domain.aggregate.entity.enumvalue.MemberRole;
import com.artlable.backend.member.command.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    //로그인
    public LoginResponseDTO login(LoginRequestDTO requestDTO) throws Exception{
        Member member = memberRepository.findByMemberEmail(requestDTO.getMemberEmail()).orElseThrow(
                () -> new BadCredentialsException("잘못된 계정정보입니다.")); // id조회실패시 exception

        if (!passwordEncoder.matches(requestDTO.getMemberPwd(), member.getMemberPwd())) {
            throw new BadCredentialsException("잘못된 계정정보입니다."); // 비밀번호 미일치시 exception
        }

        LoginResponseDTO responseDTO = new LoginResponseDTO(member);


        return responseDTO;

    }

    //회원가입
    public boolean register(SignRequestDTO requestDTO) throws Exception{
        try{
            //회원가입과정
            Member member = Member.builder()
                    .memberEmail(requestDTO.getMemberEmail())
                    .memberPwd(passwordEncoder.encode(requestDTO.getMemberPwd()))
                    .isDeleted("N")
                    .memberRole(MemberRole.MEMBER)
                    .build();

            memberRepository.save(member);
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
        return true;
    }

    //아이디 찾기
    public SignResponseDTO getMember(String memberEmail) throws Exception{
        Member member = memberRepository.findByMemberEmail(memberEmail)
                .orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
        return new SignResponseDTO(member);
    }
}
