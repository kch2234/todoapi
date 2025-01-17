package com.cicd.todoapi.dto;

import com.cicd.todoapi.domain.Member;
import com.cicd.todoapi.domain.Priority;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {
    private Long tno;
    private Member member;
    private String title;
    // 날짜를 화면에서 쉽게 처리하도록 JsonFormat 이용 -> 날짜 패턴 지정
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    private ValueDTO value;
    private CategoryDTO category;
    private Priority priority;
    private boolean complete;

    // 변환 기능 추가
    // Entity -> DTO
    /*public Todo toEntity() {
        Todo todo = Todo.builder()
                .member(member)
                .title(title)
                .dueDate(dueDate)
                .value(value)
                .category(category)
                .priority(priority)
                .complete(complete)
                .build();
        return todo;
    }*/
}
