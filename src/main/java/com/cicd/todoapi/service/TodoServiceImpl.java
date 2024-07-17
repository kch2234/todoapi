package com.cicd.todoapi.service;

import com.cicd.todoapi.domain.Category;
import com.cicd.todoapi.domain.Member;
import com.cicd.todoapi.domain.Todo;
import com.cicd.todoapi.domain.Value;
import com.cicd.todoapi.dto.CategoryDTO;
import com.cicd.todoapi.dto.TodoDTO;
import com.cicd.todoapi.dto.ValueDTO;
import com.cicd.todoapi.repository.CategoryRepository;
import com.cicd.todoapi.repository.MemberRepository;
import com.cicd.todoapi.repository.TodoRepository;
import com.cicd.todoapi.repository.ValueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;
    private final ValueRepository valueRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Long add(TodoDTO todoDTO) {
        Member member = memberRepository.findById(todoDTO.getMember().getId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Value value = null;
        if (todoDTO.getValue() != null && todoDTO.getValue().getVno() != null) {
            value = valueRepository.findById(todoDTO.getValue().getVno())
                    .orElse(null);
        } else if (todoDTO.getValue() != null && todoDTO.getValue().getValueString() != null) {
            value = Value.builder().valueString(todoDTO.getValue().getValueString()).member(member).build();
            valueRepository.save(value);
        }

        Category category = null;
        if (todoDTO.getCategory() != null && todoDTO.getCategory().getCno() != null) {
            category = categoryRepository.findById(todoDTO.getCategory().getCno())
                    .orElse(null);
        } else if (todoDTO.getCategory() != null && todoDTO.getCategory().getCategoryName() != null) {
            category = Category.builder().categoryName(todoDTO.getCategory().getCategoryName()).member(member).build();
            categoryRepository.save(category);
        }

        Todo todo = Todo.builder()
                .member(member)
                .title(todoDTO.getTitle())
                .dueDate(todoDTO.getDueDate())
                .value(value)
                .category(category)
                .priority(todoDTO.getPriority())
                .complete(todoDTO.isComplete())
                .build();

        Todo savedEntity = todoRepository.save(todo);

        TodoDTO getDTO = TodoDTO.builder()
                .tno(savedEntity.getTno())
                .title(savedEntity.getTitle())
                .dueDate(savedEntity.getDueDate())
                .value(savedEntity.getValue() != null ? ValueDTO.builder().vno(savedEntity.getValue().getVno()).valueString(savedEntity.getValue().getValueString()).build() : null)
                .category(savedEntity.getCategory() != null ? CategoryDTO.builder().cno(savedEntity.getCategory().getCno()).categoryName(savedEntity.getCategory().getCategoryName()).build() : null)
                .priority(savedEntity.getPriority())
                .complete(savedEntity.isComplete())
                .build();

        return getDTO.getTno();
    }

    @Transactional
    @Override
    public void modify(TodoDTO todoDTO) {
        Todo findTodo = todoRepository.findById(todoDTO.getTno()).orElseThrow();

        Value value = null;
        if (todoDTO.getValue() != null && todoDTO.getValue().getVno() != null) {
            value = valueRepository.findById(todoDTO.getValue().getVno())
                    .orElseThrow(() -> new IllegalArgumentException("Value not found"));
            value.setValueString(todoDTO.getValue().getValueString()); // Update valueString
        } else if (todoDTO.getValue() != null && todoDTO.getValue().getValueString() != null) {
            value = Value.builder().valueString(todoDTO.getValue().getValueString()).member(findTodo.getMember()).build();
            valueRepository.save(value);
        }

        Category category = null;
        if (todoDTO.getCategory() != null && todoDTO.getCategory().getCno() != null) {
            category = categoryRepository.findById(todoDTO.getCategory().getCno())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));
            category.setCategoryName(todoDTO.getCategory().getCategoryName()); // Update categoryName
        } else if (todoDTO.getCategory() != null && todoDTO.getCategory().getCategoryName() != null) {
            category = Category.builder().categoryName(todoDTO.getCategory().getCategoryName()).member(findTodo.getMember()).build();
            categoryRepository.save(category);
        }

        findTodo.changeTitle(todoDTO.getTitle());
        findTodo.changeValue(value != null ? value : findTodo.getValue());
        findTodo.changeCategory(category != null ? category : findTodo.getCategory());
        findTodo.changePriority(todoDTO.getPriority() != null ? todoDTO.getPriority() : null);
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

    // 빈 카테고리 리스트 조회
    @Override
    public List<TodoDTO> list(Long findMember) {
        /*List<Todo> all = todoRepository.findAll();
        log.info("all : {}", all);
        List<TodoDTO> list = all.stream()
                .map(todo -> modelMapper.map(todo, TodoDTO.class))
                .collect(Collectors.toList());
        return list;*/
        List<Todo> all = todoRepository.findAllByMemberId(findMember);
//        if (!all.isEmpty()) {// all이 비어있지 않다면
            log.info("all : {}", all);
            List<TodoDTO> list = all.stream()
                    .map(todo -> modelMapper.map(todo, TodoDTO.class))
                    .collect(Collectors.toList());
            return list;
//        }
//        return null;
    }

    @Override
    public List<TodoDTO> listByCategory(String category, Long findMember) {
        List<Todo> categoryAll = todoRepository.findAllByMemberIdAndCategoryName(findMember, category);
        log.info("CategoryAll : {}", categoryAll);
        List<TodoDTO> list = categoryAll.stream()
                .map(todo -> modelMapper.map(todo, TodoDTO.class))
                .collect(Collectors.toList());
        return list;
    }
}
