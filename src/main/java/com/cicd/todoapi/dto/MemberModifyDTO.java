package com.cicd.todoapi.dto;

import lombok.Data;

@Data
public class MemberModifyDTO {
    private String email;
    private String password;
    private String nickname;
}
