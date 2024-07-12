package com.cicd.todoapi.service;

import com.cicd.todoapi.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    // 카테고리 목록 조회
    List<CategoryDTO> categories(Long memberId);

    // 카테고리 추가
    Long addCategory(CategoryDTO categoryDTO, String memberEmail);

    // 카테고리 수정
    void modifyCategory(CategoryDTO categoryDTO, String memberEmail);

    // 카테고리 삭제
    void deleteCategory(Long cno, String memberEmail);

}
