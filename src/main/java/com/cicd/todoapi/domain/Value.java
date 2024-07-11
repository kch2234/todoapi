package com.cicd.todoapi.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Value {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vid;
    private String value;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
