package com.artlable.backend.member.command.application.service;

import com.artlable.backend.member.command.application.dto.CustomUserDetails;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import com.artlable.backend.member.command.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {

        Member member = memberRepository.findByMemberEmail(memberEmail).orElseThrow(
                () -> new UsernameNotFoundException("Invalid authentication!")
        );

        return new CustomUserDetails(member);
    }
}
