package com.cicd.todoapi.dto;

import com.cicd.todoapi.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Builder
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class MemberFormDTO {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private Role role;



}
