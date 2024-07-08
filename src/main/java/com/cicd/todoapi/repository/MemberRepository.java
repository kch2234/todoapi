package com.cicd.todoapi.repository;

import com.cicd.todoapi.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member getMemberByEmail(@Param("email") String email);

    // 이메일 중복 체크
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Member m WHERE m.email = :email")
    boolean existsByEmail(@Param("email") String email);

    // 닉네임 중복 체크
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Member m WHERE m.nickname = :nickname")
    boolean existsByNickname(@Param("nickname") String nickname);
}
