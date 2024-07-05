package com.cicd.todoapi.service;

import com.cicd.todoapi.domain.Todo;
import com.cicd.todoapi.dto.PageRequestDTO;
import com.cicd.todoapi.dto.PageResponseDTO;
import com.cicd.todoapi.dto.TodoDTO;
import com.cicd.todoapi.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        findTodo.changeContent(todoDTO.getContent());
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
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1, // 요청한페이지번호
                pageRequestDTO.getSize(), // 한페이지에보여줄 글개수
                Sort.by("tno").descending()); // tno 역순 정렬
        Page<Todo> all = todoRepository.findAll(pageable);
        log.info("all : {}", all);
        // 글 목록
            /*List<Todo> content = all.getContent();
            List<TodoDTO> list = new ArrayList<>();
            for(int i = 0; i < content.size(); i++) {
            Todo todo = content.get(i);
            TodoDTO dto = modelMapper.map(todo, TodoDTO.class);
            list.add(dto);
            }*/
        List<TodoDTO> list = all.getContent().stream()
                .map(todo -> modelMapper.map(todo, TodoDTO.class))
                .collect(Collectors.toList());
        // 전체 글 개수
        long totalCount = all.getTotalElements();
        PageResponseDTO<TodoDTO> responseDTO = new PageResponseDTO<>(list,
                pageRequestDTO, totalCount);
        return responseDTO;
    }
}
