package com.cicd.todoapi.controller;

import com.cicd.todoapi.domain.Member;
import com.cicd.todoapi.dto.MemberFormDTO;
import com.cicd.todoapi.dto.TodoDTO;
import com.cicd.todoapi.service.*;
import com.cicd.todoapi.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todo")
@Slf4j
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final MemberService memberService;
    private final MemberServiceImpl memberServiceImpl;
    private final CategoryService categoryService;
    private final ValueService valueService;

    // TodoList 조회
    @GetMapping("/list")
    public List<TodoDTO> list() {
        log.info("******* TodoController GET /list");
        List<TodoDTO> list = todoService.list();
        return list;
    }

    // 할일 등록 처리
    @PostMapping("/")
    public Map<String, Long> add(@RequestBody TodoDTO todoDTO, @RequestHeader("Authorization") String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) {
            log.error("User is not authenticated");
            throw new IllegalArgumentException("User is not authenticated");
        }
        Map<String, Object> member = JWTUtil.validateToken(auth.substring(7));
        log.info("Authenticated user's email: {}", member.get("email"));
        MemberFormDTO MemberEmail = memberService.findMemberByEmail(member.get("email").toString());
        // MemberFormDTO -> Member로 변환 = entityToDto
        Member findMember = memberServiceImpl.dtoToEntity(MemberEmail);
        todoDTO.setMember(findMember);
        log.info("******** TodoController POST /add - todoDTO : {}", todoDTO);
        Long tno = todoService.add(todoDTO);
        return Map.of("tno", tno);
    }

    // 수정 요청
    @PutMapping("/{tno}")
    public Map<String, String> modify(
            @PathVariable("tno") Long tno,
            @RequestBody TodoDTO todoDTO) {

        log.info("******** TodoController PUT /modify - tno : {}", tno);
        log.info("******** TodoController PUT /modify - todoDTO : {}", todoDTO);

        todoDTO.setTno(tno); // todoDTO에 path variable로 꺼낸 tno 추가
        todoService.modify(todoDTO);
        return Map.of("Result", "SUCCESS");
    }

    // 삭제 요청
    @DeleteMapping("/{tno}")
    public Map<String, String> remove(@PathVariable("tno") Long tno) {
        todoService.remove(tno);
        return Map.of("Result", "SUCCESS");
    }

    // TodoList 카테고리 별 조회
    @GetMapping("/list/{category}")
    public List<TodoDTO> listByCategory(@PathVariable("category") String category) {
        log.info("******* TodoController GET /list/{category} - category : {}", category);
        List<TodoDTO> list = categoryService.listByCategory(category);
        return list;
    }
/*
    // 멤버아이디로 해당되는 카테고리들 조회
    @GetMapping("/categories")
    public List<String> categories(@RequestHeader("Authorization") String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) {
            log.error("User is not authenticated");
            throw new IllegalArgumentException("User is not authenticated");
        }
        Map<String, Object> member = JWTUtil.validateToken(auth.substring(7));
        log.info("Authenticated user's email: {}", member.get("email"));
        MemberFormDTO MemberEmail = memberService.findMemberByEmail(member.get("email").toString());
        // MemberFormDTO -> Member로 변환 = entityToDto
        Member findMember = memberServiceImpl.dtoToEntity(MemberEmail);
        List<String> categories = categoryService.categories(findMember);
        return categories;
    }

    // 카테고리 추가
    @PostMapping("/category")
    public Map<String, String> addCategory(@RequestBody TodoDTO todoDTO) {
        log.info("******** TodoController POST /category - todoDTO : {}", todoDTO);
        categoryService.addCategory(todoDTO);
        return Map.of("Result", "SUCCESS");
    }

    // 카테고리 수정
    @PutMapping("/category/{category}")
    public Map<String, String> modifyCategory(
            @PathVariable("category") String category,
            @RequestBody TodoDTO todoDTO) {
        log.info("******** TodoController PUT /category/{category} - category : {}", category);
        log.info("******** TodoController PUT /category/{category} - todoDTO : {}", todoDTO);
        todoDTO.setCategory(category); // todoDTO에 path variable로 꺼낸 category 추가
        categoryService.modifyCategory(todoDTO);
        return Map.of("Result", "SUCCESS");
    }

    // 카테고리 삭제
    @DeleteMapping("/delCategory/{category}")
    public Map<String, String> removeCategory(@PathVariable("category") String category) {
        categoryService.removeCategory(category);
        return Map.of("Result", "SUCCESS");
    }

    // 멤버아이디로 해당되는 값들 조회
    @GetMapping("/values")
    public List<String> values(@RequestHeader("Authorization") String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) {
            log.error("User is not authenticated");
            throw new IllegalArgumentException("User is not authenticated");
        }
        Map<String, Object> member = JWTUtil.validateToken(auth.substring(7));
        log.info("Authenticated user's email: {}", member.get("email"));
        MemberFormDTO MemberEmail = memberService.findMemberByEmail(member.get("email").toString());
        // MemberFormDTO -> Member로 변환 = entityToDto
        Member findMember = memberServiceImpl.dtoToEntity(MemberEmail);
        List<String> values = valueService.values(findMember);
        return values;
    }

    // 값 추가
    @PostMapping("/value")
    public Map<String, String> addValue(@RequestBody TodoDTO todoDTO) {
        log.info("******** TodoController POST /value - todoDTO : {}", todoDTO);
        valueService.addValue(todoDTO);
        return Map.of("Result", "SUCCESS");
    }

    // 값 수정
    @PutMapping("/value/{value}")
    public Map<String, String> modifyValue(
            @PathVariable("value") String value,
            @RequestBody TodoDTO todoDTO) {
        log.info("******** TodoController PUT /value/{value} - value : {}", value);
        log.info("******** TodoController PUT /value/{value} - todoDTO : {}", todoDTO);
        todoDTO.setValue(value); // todoDTO에 path variable로 꺼낸 value 추가
        valueService.modifyValue(todoDTO);
        return Map.of("Result", "SUCCESS");
    }

    // 값 삭제
    @DeleteMapping("/delValue/{value}")
    public Map<String, String> removeValue(@PathVariable("value") String value) {
        valueService.removeValue(value);
        return Map.of("Result", "SUCCESS");
    }*/

}
