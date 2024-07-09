package com.cicd.todoapi.controller;

import com.cicd.todoapi.dto.MemberFormDTO;
import com.cicd.todoapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
