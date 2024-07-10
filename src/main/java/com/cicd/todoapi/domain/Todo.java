package com.cicd.todoapi.domain;

import jdk.jfr.Name;
import lombok.*;

import javax.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;  // 작성자

    @Column(nullable = false) // null 허용하지 않음
    private String title;   // 할일이름
//    private String content; // 할일내용
    private LocalDate dueDate;  // 할일 일정 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VALUE")
    private Value value;      // 할일 value

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY")
    private Category category;    // 할일 카테고리

    private String priority;    // 할일 중요도
    private boolean complete;   // 할일 완료 여부

    // 수정 가능한 필드를 위한 수정 메서드 추가
    public void changeTitle(String title) {
        this.title = title;
    }
/*    public void changeContent(String content) {
        this.content = content;
    }*/
    public void changeDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public void changeValue(Value value) {
        this.value = value;
    }
    public void changeCategory(Category category) {
        this.category = category;
    }
    public void changePriority(String priority) {
        this.priority = priority;
    }
    public void changeComplete(boolean complete) {
        this.complete = complete;
    }
}
