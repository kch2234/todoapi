package com.cicd.todoapi.repository;

import com.cicd.todoapi.domain.Member;
import com.cicd.todoapi.domain.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional // EntityManager 사용위해
@Rollback(value = false) // 테스트에서는 자동롤백이 되므로, DB에 수정결과 유지하기 위해 롤백안되게 설정
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsert() {
        for (int i = 0; i < 10; i++) {
            Member member = Member.builder()
                    .email("user" + i + "@test.com")
                    .password(passwordEncoder.encode("1234"))
                    .nickname("User" + i)
                    .role(Role.USER)
                    .build();
            memberRepository.save(member);
        }// for
    }

    // 회원 한개 조회
    @Test
    public void testRead() {
        String email = "user9@test.com";
        Member findMember = memberRepository.getMemberByEmail(email);
        log.info("********** member : {}", findMember);
    }
}