package com.cicd.todoapi.controller;

import com.cicd.todoapi.service.CategoryService;
import com.cicd.todoapi.service.MemberServiceImpl;
import com.cicd.todoapi.service.ValueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/api/todo")
@Slf4j
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final ValueService valueService;
    private final MemberServiceImpl memberServiceImpl;


}
