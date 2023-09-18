package com.artlable.backend.member.command.domain.repository;

import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberEmail(String memberEmail); //아이디를 기준으로 회원정보 조회

}
