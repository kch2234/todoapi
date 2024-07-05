package com.cicd.todoapi.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor // Builder 때문에 추가
@NoArgsConstructor  // JPA 엔티티라서 추가
public class Todo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tno;       // 고유번호
    // many to one 으로 변경
    private String member;  // 작성자
    private String title;   // 할일이름
    private String content; // 할일내용
    private LocalDate dueDate;  // 할일 일정 날짜
    // many to one 으로 변경
    private String value;      // 할일 value
    private String category;    // 할일 카테고리
    // many to one 으로 변경
    private String priority;    // 할일 중요도
    private boolean complete;   // 할일 완료 여부

    // 수정 가능한 필드를 위한 수정 메서드 추가
    public void changeTitle(String title) {
        this.title = title;
    }
    public void changeContent(String content) {
        this.content = content;
    }
    public void changeDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public void changeValue(String value) {
        this.value = value;
    }
    public void changeCategory(String category) {
        this.category = category;
    }
    public void changePriority(String priority) {
        this.priority = priority;
    }
    public void changeComplete(boolean complete) {
        this.complete = complete;
    }
}
