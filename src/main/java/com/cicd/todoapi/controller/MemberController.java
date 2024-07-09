package com.cicd.todoapi.controller;

import com.cicd.todoapi.dto.MemberFormDTO;
import com.cicd.todoapi.dto.PageRequestDTO;
import com.cicd.todoapi.dto.PageResponseDTO;
import com.cicd.todoapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/checkEmail")
  public ResponseEntity<Boolean> checkEmail(@RequestBody MemberFormDTO memberFormDTO) {

    log.info("***** MemberController /checkEmail - memberFormDTO : {}", memberFormDTO);
    log.info("***** MemberController /checkEmail - email : {}", memberFormDTO.getEmail());

    String email = memberFormDTO.getEmail();
    ResponseEntity<Boolean> res = null;
    try {
      Boolean result = memberService.checkEmail(email);
      log.info("***** MemberController /checkEmail - 중복 결과 : {}", result);
      res = new ResponseEntity<Boolean>(result, HttpStatus.OK);

    } catch (Exception e) {
      res = new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
    }
    return res;
  }

  @PostMapping("/checkNickname")
  public ResponseEntity<Boolean> checkNickname(@RequestBody MemberFormDTO memberFormDTO) {

    log.info("***** MemberController /checkEmail - nickname : {}", memberFormDTO.getNickname());

    String nickname = memberFormDTO.getNickname();
    ResponseEntity<Boolean> res = null;
    try {
      Boolean result = memberService.checkNickname(nickname);
      log.info("***** MemberController /checkNickname - 중복 결과 : {}", result);
      res = new ResponseEntity<Boolean>(result, HttpStatus.OK);

    } catch (Exception e) {
      res = new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
    }
    return res;
  }

  @PreAuthorize("hasAnyAuthority('ROLE_USER')")
  @GetMapping("/getMember")
  public MemberFormDTO getMember(Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new IllegalStateException("User not authenticated");
    }

    String email = authentication.getName();
    log.info("***** MemberController /getMember - email : {}", email);

    MemberFormDTO result = memberService.getMember(email);

    return result;
  }
}
