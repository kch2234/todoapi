package com.cicd.todoapi.service;

import com.cicd.todoapi.domain.Member;
import com.cicd.todoapi.dto.TodoDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class TodoServiceImplTest {

    @Autowired
    private TodoService todoService;

    @Test
    public void testAdd() {
        // 저장 테스트 해볼 TodoDTO 필요
        TodoDTO todoDTO = TodoDTO.builder()
                .title("서비스 테스트")
                .member(Member.builder().email("user01@test.com").build())
                .dueDate(LocalDate.of(2024, 5, 5))
                .build();
        log.info("todoDTO : {}", todoDTO);
        // 저장!
        Long savedTno = todoService.add(todoDTO);
        log.info("savedTno :{}", savedTno);
    }

    @Test
    public void getTest() {
        TodoDTO todoDTO = todoService.get(101L);
        log.info("find dto : {}", todoDTO);
    }

}