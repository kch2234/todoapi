package com.cicd.todoapi.service;

import com.cicd.todoapi.dto.TodoDTO;

import java.util.List;

public interface CategoryService {
    // 카테고리 별 조회
    List<TodoDTO> listByCategory(String category);
}
