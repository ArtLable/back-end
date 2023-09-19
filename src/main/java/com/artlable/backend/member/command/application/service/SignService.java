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
    public void register(SignRequestDTO requestDTO) throws Exception{
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
            throw new Exception("잘못된 요청입니다.");
        }
    }

    //닉네임 중복조회
    @Transactional(readOnly = true)
    public void checkDuplicateMemberNickname(String memberNickname) throws Exception {

        if (memberNickname == null || memberNickname.trim().isEmpty()) {
            throw new IllegalArgumentException("이메일 값이 유효하지 않습니다.");
        } else if(memberRepository.existsByMemberNickname(memberNickname)){
            throw new IllegalArgumentException("사용중인 닉네임 입니다.");
        }
    }

    //아이디 중복조회
    @Transactional(readOnly = true)
    public void checkDuplicateMemberEmail(String memberEmail) throws Exception {

        if (memberEmail == null || memberEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("이메일 값이 유효하지 않습니다.");
        }else if(memberRepository.existsByMemberEmail(memberEmail)){
            throw new IllegalArgumentException("사용중인 이메일 입니다.");
        }
    }

    //회원탈퇴
    @Transactional
    public void deleteMember(Long memberNo) {
        Member member = memberRepository.findMemberByMemberNo(memberNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
        memberRepository.deleteByMemberNo(memberNo);
    }
    //비밀번호변경
    @Transactional
    public void changeMemberPwd(UpdatePwdRequestDTO requestDTO,Long memberNo) {
        Member member = memberRepository.findMemberByMemberNo(memberNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));

        if (!passwordEncoder.matches(requestDTO.getCurrentPassword(), member.getMemberPwd())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }else if(!passwordEncoder.matches(requestDTO.getCurrentPassword(),requestDTO.getMemberPwd())){
            throw new IllegalArgumentException("같은 비밀번호는 변경할수 없습니다.");
        }

        member.setMemberPwd(passwordEncoder.encode(requestDTO.getMemberPwd()));
        memberRepository.save(member);
    }

    //회원정보변경
    // 회원 정보 변경
    @Transactional
    public void updateMemberInfo(UpdateInfoRequestDTO requestDTO,Long memberNo) {
        Member member = memberRepository.findMemberByMemberNo(memberNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));

        member.setMemberNickname(requestDTO.getMemberNickname());
        memberRepository.save(member);
    }
}

