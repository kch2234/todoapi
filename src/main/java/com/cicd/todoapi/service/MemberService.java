package com.cicd.todoapi.service;

import com.cicd.todoapi.dto.MemberFormDTO;

public interface MemberService {
    Long signup(MemberFormDTO memberFormDTO);
    Boolean checkEmail(String email);
    Boolean checkNickname(String nickname);

//    List<MemberFormDTO> getAllMembers();
//    PageResponseDTO<MemberFormDTO> getList(PageRequestDTO pageRequestDTO);
}
