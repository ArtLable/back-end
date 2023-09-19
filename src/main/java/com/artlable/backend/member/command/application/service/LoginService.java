package com.artlable.backend.member.command.application.service;

import com.artlable.backend.jwt.TokenProvider;
import com.artlable.backend.member.command.application.dto.auth.AuthResponseDTO;
import com.artlable.backend.member.command.application.dto.login.LoginRequestDTO;
import com.artlable.backend.member.command.application.dto.login.LoginResponseDTO;
import com.artlable.backend.member.command.domain.aggregate.entity.Authority;
import com.artlable.backend.member.command.domain.repository.AuthorityRepository;
import com.artlable.backend.member.command.domain.repository.LoginRepository;
import com.artlable.backend.member.command.application.dto.sign.SignRequestDTO;
import com.artlable.backend.member.command.application.dto.sign.SignResponseDTO;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.member.command.domain.aggregate.entity.enumvalue.MemberSocialLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    //로그인
    @Transactional
    public Map<String, Object> login(LoginRequestDTO requestDTO) throws Exception {
        Member member = loginRepository.findByMemberEmail(requestDTO.getMemberEmail()).orElseThrow(
                () -> new BadCredentialsException("잘못된 계정정보입니다.")); // id조회실패시 exception

        if (!passwordEncoder.matches(requestDTO.getMemberPwd(), member.getMemberPwd())) {
            throw new BadCredentialsException("잘못된 계정정보입니다."); // 비밀번호 미일치시 exception
        }

        // 멤버 정보를 기반으로 JWT 액세스 토큰 생성
        String accessToken = tokenProvider.createAccessToken(
                new UsernamePasswordAuthenticationToken(
                        member.getMemberEmail(),
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority(member.getMemberRole().name()))
                )
        );

        String refreshToken = tokenProvider.createRefreshToken();

        // 처음로그인
        Authority authority = authorityRepository.findByMember(member)
                .orElseGet(() -> Authority.builder()
                        .member(member)
                        .build());

        authority.updateToken("Bearer", accessToken, refreshToken, MemberSocialLogin.LOCAL);
        // 생성된 액세스 토큰을 Authority 엔티티에 저장
        authorityRepository.save(authority);

        AuthResponseDTO authResponse = new AuthResponseDTO(accessToken, MemberSocialLogin.LOCAL);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("loginResponse", LoginResponseDTO.builder()
                .memberNo(member.getMemberNo())
                .memberNickname(member.getMemberNickname())
                .memberImage(member.getMemberImage())
                .memberRole(member.getMemberRole())
                .authority(Collections.singletonList(authResponse))
                .build());
        resultMap.put("refreshToken", refreshToken);

        return resultMap;
    }

    //회원정보조회
    @Transactional(readOnly = true)
    public SignResponseDTO getMember(SignRequestDTO requestDTO) throws Exception{
        Member member = loginRepository.findByMemberEmail(requestDTO.getMemberEmail())
                .orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
        return new SignResponseDTO(member);
    }
}
