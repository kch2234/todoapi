package com.cicd.todoapi.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;
    private String category;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
