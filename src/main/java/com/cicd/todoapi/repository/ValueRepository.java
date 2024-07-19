package com.cicd.todoapi.repository;

import com.cicd.todoapi.domain.Value;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValueRepository extends JpaRepository<Value, Long> {

    List<Value> findByValueString(String valueString);
}
