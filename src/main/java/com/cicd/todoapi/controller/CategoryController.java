package com.cicd.todoapi.controller;

import com.cicd.todoapi.dto.TodoDTO;
import com.cicd.todoapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // TodoList 카테고리 별 조회
    @GetMapping("/list/{category}")
    public List<TodoDTO> listByCategory(@PathVariable("category") String category) {
        log.info("******* TodoController GET /list/{category} - category : {}", category);
        List<TodoDTO> list = categoryService.listByCategory(category);
        return list;
    }

}
