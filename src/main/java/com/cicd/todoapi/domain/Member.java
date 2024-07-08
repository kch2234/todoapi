package com.cicd.todoapi.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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