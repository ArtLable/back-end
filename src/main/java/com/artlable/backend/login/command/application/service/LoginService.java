package com.artlable.backend.login.command.application.service;

import com.artlable.backend.jwt.TokenProvider;
import com.artlable.backend.login.command.application.dto.auth.AuthResponseDTO;
import com.artlable.backend.login.command.application.dto.login.LoginRequestDTO;
import com.artlable.backend.login.command.application.dto.login.LoginResponseDTO;
import com.artlable.backend.login.command.domain.agreegate.entity.Authority;
import com.artlable.backend.login.command.domain.repository.AuthorityRepository;
import com.artlable.backend.login.command.domain.repository.LoginRepository;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.member.command.domain.aggregate.entity.enumvalue.MemberSocialLogin;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final ModelMapper modelMapper;

    //로그인
    @Transactional
    public LoginResponseDTO login(LoginRequestDTO requestDTO) throws Exception{
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

        // 생성된 액세스 토큰을 Authority 엔티티에 저장
        // 처음로그인
        Authority authority = authorityRepository.findByMember(member)
                .orElseGet(() -> {
                    Authority newAuthority = Authority.builder()
                            .tokenType("Bearer")
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .memberSocialLogin(MemberSocialLogin.LOCAL)
                            .member(member)
                            .build();
                    return newAuthority;
                });
        //이후 로그인
        authority.setTokenType("Bearer");
        authority.setAccessToken(accessToken);
        authority.setRefreshToken(refreshToken);
        authority.setMemberSocialLogin(MemberSocialLogin.LOCAL);

        authorityRepository.save(authority);

        // Authority 엔터티 리스트를 AuthResponseDTO 리스트로 변환
        List<AuthResponseDTO> authResponseDTOList = member.getAuthority().stream()
                .map(auth -> modelMapper.map(auth, AuthResponseDTO.class))
                .collect(Collectors.toList());


        // 로그인 응답 DTO 생성 및 반환
        LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder()
                .memberEmail(member.getMemberEmail())
                .memberImage(member.getMemberImage())
                .memberRole(member.getMemberRole())
                .authority(authResponseDTOList)
                .build();

        return loginResponseDTO;
    }
}
