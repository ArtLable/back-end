package com.artlable.backend.member.command.domain.repository;

import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository <Member, Long> {

    Optional<Member> findByMemberEmail(String email); // 조회결과 없을수 있음
    boolean existsByMemberEmail(String email); // 이메일 중복조회
}
