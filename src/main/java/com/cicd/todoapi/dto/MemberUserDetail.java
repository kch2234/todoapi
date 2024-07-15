package com.cicd.todoapi.dto;

import com.cicd.todoapi.domain.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MemberUserDetail extends User {

    private Long id;
    private String email;
    private String password;
    private Role role;

    public MemberUserDetail(Long id, String email, String nickname, String password, Role role) {
        super(email, password, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()))); // 시큐리티를 위한 부모 생성자 호출
        // 위 변수값 초기화
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Map<String, Object> getClaims() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("email", email);
        map.put("password", password);
        map.put("role", role);
        return map;
    }
}
