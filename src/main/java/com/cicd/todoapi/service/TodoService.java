package com.cicd.todoapi.service;

import com.cicd.todoapi.dto.PageRequestDTO;
import com.cicd.todoapi.dto.PageResponseDTO;
import com.cicd.todoapi.dto.TodoDTO;

public interface TodoService {
    // 할일 등록(저장) 기능
    Long add(TodoDTO todoDTO);
    // 조회
    TodoDTO get(Long tno);
    // 수정
    void modify(TodoDTO todoDTO);
    // 삭제
    void remove(Long tno);

    // 목록 조회
    PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO);

}
