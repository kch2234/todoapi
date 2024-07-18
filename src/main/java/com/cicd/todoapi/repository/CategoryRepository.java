package com.cicd.todoapi.repository;

import com.cicd.todoapi.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c where c.member.id = :findMember")
    List<Category> findMemberIdByCategory(Long findMember);
}
