package com.cicd.todoapi.service;

import com.cicd.todoapi.dto.TodoDTO;

import java.util.List;

public interface TodoService {
    // 할일 등록(저장) 기능
    Long add(TodoDTO todoDTO);
    // 수정
    void modify(TodoDTO todoDTO);
    // 삭제
    void remove(Long tno);

    // 목록 조회
    List<TodoDTO> list(Long findMember);

    // 카테고리별 조회
    List<TodoDTO> listByCategory(String category, Long findMember);

}
