package com.cicd.todoapi.repository;

import com.cicd.todoapi.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    // 카테고리 별 조회
    @Query("select t from Todo t where t.category = :category")
    List<Todo> findByCategory(String category);
}
