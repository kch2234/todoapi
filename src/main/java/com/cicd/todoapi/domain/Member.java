package com.cicd.todoapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude = "roleList")
public class Member {
    @Id
    private Long id;  // 고유번호
    private String email;  // 로그인 아읻디는 email
    private String password;
    private String nickname;

    private Role role; // 권한
    /*
    private boolean social; //  소셜 회원 여부
    */

    // 필드 수정 메서드
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }
    public void changePassword(String password) {
        this.password = password;
    }
    public void changeRole(Role role) {
        this.role = role;
    }
    /*public void changeSocial(boolean social) {
        this.social = social;
    }*/
}