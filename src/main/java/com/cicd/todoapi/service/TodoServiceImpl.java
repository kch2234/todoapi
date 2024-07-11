package com.cicd.todoapi.service;

import com.cicd.todoapi.domain.Member;
import com.cicd.todoapi.domain.Todo;
import com.cicd.todoapi.domain.Value;
import com.cicd.todoapi.dto.TodoDTO;
import com.cicd.todoapi.repository.MemberRepository;
import com.cicd.todoapi.repository.TodoRepository;
import com.cicd.todoapi.repository.ValueRepository;
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
    private final ValueRepository valueRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long add(TodoDTO todoDTO) {
        Member member = memberRepository.findById(todoDTO.getMember().getId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Value value = valueRepository.findById(todoDTO.getValue().getValue())
                .orElseGet(() -> {
                    Value newValue = Value.builder()
                            .value(todoDTO.getValue().getValue())
                            .member(member)
                            .build();
                    return valueRepository.save(newValue);
                });
        Todo entity = Todo.builder()
                .member(member)
                .title(todoDTO.getTitle())
                .dueDate(todoDTO.getDueDate())
                .value(value)
                .category(todoDTO.getCategory())
                .priority(todoDTO.getPriority())
                .complete(todoDTO.isComplete())
                .build();
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
        Value value = valueRepository.findById(todoDTO.getValue().getValue())
                .orElseGet(() -> {
                    Value newValue = Value.builder()
                            .value(todoDTO.getValue().getValue())
                            .member(findTodo.getMember())
                            .build();
                    return valueRepository.save(newValue);
                });

        findTodo.changeTitle(todoDTO.getTitle());
        findTodo.changeValue(value);
        findTodo.changeCategory(todoDTO.getCategory());
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
