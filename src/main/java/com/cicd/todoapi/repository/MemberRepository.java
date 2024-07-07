package com.cicd.todoapi.repository;

import com.cicd.todoapi.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member getMemberByEmail(@Param("email") String email);
}
