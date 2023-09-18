package com.artlable.backend.member.command.domain.repository;

import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository <Member, Long> {

    Optional<Member> findByMemberEmail(String memberEmail); //아이디를 기준으로 회원정보 조회

    Optional<Member> findMemberByMemberNo(Long memberNo); //회원번호로 회원정보 조회
    boolean existsByMemberEmail(String memberEmail); //이메일 중복조회
    boolean existsByMemberNickname(String memberNickname); //닉네임 중복조회


}
