package com.cicd.todoapi.repository;

import com.cicd.todoapi.domain.Value;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValueRepository extends JpaRepository<Value, String> {

}
