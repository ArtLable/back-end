package com.artlable.backend.member.command.application.service;

import com.artlable.backend.member.command.application.dto.sign.SignRequestDTO;
import com.artlable.backend.member.command.application.dto.update.UpdateInfoRequestDTO;
import com.artlable.backend.member.command.application.dto.update.UpdatePwdRequestDTO;
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
        try{
            //회원가입과정
            Member member = Member.builder()
                    .memberEmail(requestDTO.getMemberEmail())
                    .memberPwd(passwordEncoder.encode(requestDTO.getMemberPwd()))
                    .memberNickname(requestDTO.getMemberNickname())
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

    //닉네임 중복조회
    @Transactional(readOnly = true)
    public boolean checkDuplicateMemberNickname(String memberNickname) throws Exception {

        if(memberRepository.existsByMemberNickname(memberNickname)){
            throw new Exception("사용중인 닉네임 입니다.");
            }
        return true;
    }

    //아이디 중복조회
    @Transactional(readOnly = true)
    public boolean checkDuplicateMemberEmail(String memberEmail) throws Exception {

        if (memberEmail == null || memberEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("이메일 값이 유효하지 않습니다.");
        }

        if(memberRepository.existsByMemberEmail(memberEmail)){
            throw new Exception("사용중인 이메일 입니다.");
        }
        return true;
    }

    //회원탈퇴
    @Transactional
    public void deleteMember(Long memberNo) {
        Member member = memberRepository.findMemberByMemberNo(memberNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));

        memberRepository.delete(member);
    }
    //비밀번호변경
    @Transactional
    public void changeMemberPwd(UpdatePwdRequestDTO requestDTO, Long memberNo) {
        Member member = memberRepository.findMemberByMemberNo(memberNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));

        if (!passwordEncoder.matches(requestDTO.getCurrentPassword(), member.getMemberPwd())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        member.setMemberPwd(passwordEncoder.encode(requestDTO.getMemberPwd()));
        memberRepository.save(member);
    }

    //회원정보변경
    // 회원 정보 변경
    @Transactional
    public void updateMemberInfo(UpdateInfoRequestDTO requestDTO, Long memberNo) {
        Member member = memberRepository.findMemberByMemberNo(memberNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));

        member.setMemberNickname(requestDTO.getMemberNickname());
        memberRepository.save(member);
    }
}

