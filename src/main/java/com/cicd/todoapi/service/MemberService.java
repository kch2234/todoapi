package com.cicd.todoapi.service;

import com.cicd.todoapi.dto.MemberFormDTO;
import com.cicd.todoapi.dto.MemberModifyDTO;

public interface MemberService {
    Long signup(MemberFormDTO memberFormDTO);
    Boolean checkEmail(String email);
    Boolean checkNickname(String nickname);
    MemberFormDTO getMember(String email);
    void modifyMember(MemberModifyDTO memberModifyDTO);

    MemberFormDTO findMemberByEmail(String email);
}
