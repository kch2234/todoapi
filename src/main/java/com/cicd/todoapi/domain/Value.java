package com.cicd.todoapi.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString(exclude = "member")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Value {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vno;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @Setter
    private String valueString;

    // 수정 가능한 필드를 위한 수정 메서드 추가
    public void changeValueString(String valueString) {
        this.valueString = valueString;
    }

}
