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
    private String valueString;
}
