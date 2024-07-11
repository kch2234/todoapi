package com.cicd.todoapi.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString(exclude = "member")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    private String categoryName;

    // 필드 수정 메서드 추가
    public void changeCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
