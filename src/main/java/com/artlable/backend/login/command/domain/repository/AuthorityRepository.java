package com.artlable.backend.login.command.domain.repository;

import com.artlable.backend.login.command.domain.agreegate.entity.Authority;
import com.artlable.backend.member.command.domain.aggregate.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByMember(Member member);
}
