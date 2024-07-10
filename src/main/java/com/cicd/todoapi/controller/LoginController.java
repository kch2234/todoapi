package com.cicd.todoapi.controller;

import com.cicd.todoapi.dto.MemberFormDTO;
import com.cicd.todoapi.dto.MemberModifyDTO;
import com.cicd.todoapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Long> signup(MemberFormDTO memberFormDTO) {
        Long memberId = memberService.signup(memberFormDTO);
        return ResponseEntity.ok(memberId);
    }

    // 프로필 조회
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("/getMember")
    public MemberFormDTO getMember(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("***** LoginController getMember - 인증되지 않은 사용자입니다");
        }

        String email = authentication.getName();
        log.info("***** LoginController /getMember - email : {}", email);

        MemberFormDTO result = memberService.getMember(email);

        return result;
    }

    // 프로필 수정
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PutMapping("/modifyMember")
    public ResponseEntity<String> modifyMember(@RequestBody MemberModifyDTO memberModifyDTO) {
        try {
            memberService.modifyMember(memberModifyDTO);
            return ResponseEntity.ok("Member modified successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to modify member: " + e.getMessage());
        }
    }

}
