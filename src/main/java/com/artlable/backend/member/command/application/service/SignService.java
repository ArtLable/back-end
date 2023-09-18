package com.artlable.backend.member.command.application.service;

import com.artlable.backend.member.command.application.dto.sign.SignRequestDTO;
import com.artlable.backend.member.command.application.dto.sign.SignResponseDTO;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.member.command.domain.aggregate.entity.enumvalue.MemberRole;
import com.artlable.backend.member.command.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public boolean register(SignRequestDTO requestDTO) throws Exception{
        if (memberRepository.existsByMemberEmail(requestDTO.getMemberEmail())) {
            throw new Exception("중복된 이메일입니다.");
        }

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
    @Transactional(readOnly = true)
    public SignResponseDTO getMember(String memberEmail) throws Exception{
        Member member = memberRepository.findByMemberEmail(memberEmail)
                .orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
        return new SignResponseDTO(member);
    }

}
