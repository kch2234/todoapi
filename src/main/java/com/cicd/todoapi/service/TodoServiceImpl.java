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
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long add(TodoDTO todoDTO) {
        // todoDTO를 Entity로 변환해 Repository의 저장 기능 호출
        /*Todo entity = todoDTO.toEntity();*/
        // todoDTO를 Entity로 변환해 Repository의 저장 기능 호출
        Todo entity = modelMapper.map(todoDTO, Todo.class);
                                    // (타겟객체, 변환하고싶은클래스타입)
        Todo savedEntity = todoRepository.save(entity);
        return savedEntity.getTno();
    }

    @Override
    public TodoDTO get(Long tno) {
        Todo findTodo = todoRepository.findById(tno).orElseThrow();
        // Entity -> DTO
        TodoDTO dto = modelMapper.map(findTodo, TodoDTO.class);
        return dto;
    }

    @Transactional
    @Override
    public void modify(TodoDTO todoDTO) {
        Todo findTodo = todoRepository.findById(todoDTO.getTno()).orElseThrow();
        findTodo.changeTitle(todoDTO.getTitle());
//        findTodo.changeContent(todoDTO.getContent());
        // ValueDTO, CategoryDTO 추가
        findTodo.changePriority(todoDTO.getPriority());
        findTodo.changeComplete(todoDTO.isComplete());
        findTodo.changeDueDate(todoDTO.getDueDate());
    }

    @Transactional
    @Override
    public void remove(Long tno) {
        Todo findTodo = todoRepository.findById(tno).orElse(null);
        if(findTodo != null) {
            todoRepository.delete(findTodo);
        }else {
            log.info("삭제 실패.....");
        }
    }

    @Override
    public List<TodoDTO> list() {
        List<Todo> all = todoRepository.findAll();
        log.info("all : {}", all);
        List<TodoDTO> list = all.stream()
                .map(todo -> modelMapper.map(todo, TodoDTO.class))
                .collect(Collectors.toList());
        return list;
    }
}
