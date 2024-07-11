package com.cicd.todoapi.service;

import com.cicd.todoapi.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    // 카테고리 별 조회
    List<CategoryDTO> listCategory(String categoryName ,String memberEmail);

    // 카테고리 추가
    Long addCategory(CategoryDTO categoryDTO, String memberEmail);

    // 카테고리 수정
    void modifyCategory(CategoryDTO categoryDTO, String memberEmail);

    // 카테고리 삭제
    void deleteCategory(Long cno, String memberEmail);

}
