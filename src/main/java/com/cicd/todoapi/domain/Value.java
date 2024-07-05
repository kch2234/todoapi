package com.cicd.todoapi.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Value {
    @Id
    private String value;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
