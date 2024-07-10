package com.cicd.todoapi.service;

import com.cicd.todoapi.domain.Todo;
import com.cicd.todoapi.dto.TodoDTO;
import com.cicd.todoapi.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService{

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<TodoDTO> listByCategory(String category) {
        List<Todo> allCategory = todoRepository.findByCategory(category);
        log.info("allCategory : {}", allCategory);
        List<TodoDTO> categoryList = allCategory.stream()
                .map(todo -> modelMapper.map(todo, TodoDTO.class))
                .collect(Collectors.toList());
        return categoryList;
    }
}
