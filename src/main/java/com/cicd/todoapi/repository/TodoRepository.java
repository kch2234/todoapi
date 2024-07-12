package com.cicd.todoapi.repository;

import com.cicd.todoapi.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t WHERE t.member.id = :memberId")
    List<Todo> findAllByMemberId(Long memberId);

    @Query("SELECT t FROM Todo t WHERE t.member.id = :memberId AND t.category.categoryName = :categoryName")
    List<Todo> findAllByMemberIdAndCategoryName(Long memberId, String categoryName);
}
