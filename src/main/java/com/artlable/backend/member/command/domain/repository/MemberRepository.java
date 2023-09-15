package com.artlable.backend.member.command.domain.repository;

import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository <Member, Long> {

    Optional<Member> findByMemberEmail(String memberEmail); // 이메일 조회

}
