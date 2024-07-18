package com.cicd.todoapi.controller;

import com.cicd.todoapi.domain.Member;
import com.cicd.todoapi.dto.CategoryDTO;
import com.cicd.todoapi.dto.MemberFormDTO;
import com.cicd.todoapi.service.*;
import com.cicd.todoapi.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todo/")
@Slf4j
@RequiredArgsConstructor
public class CategoryController {
    private final TodoService todoService;
    private final MemberService memberService;
    private final CategoryService categoryService;
    private final ValueService valueService;
    private final MemberServiceImpl memberServiceImpl;

    // 멤버아이디로 해당되는 카테고리들 조회
    @GetMapping("/categories")
    public List<CategoryDTO> categories(@RequestHeader("Authorization") String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) {
            log.error("User is not authenticated");
            throw new IllegalArgumentException("User is not authenticated");
        }
        Map<String, Object> member = JWTUtil.validateToken(auth.substring(7));
        log.info("Authenticated user's email: {}", member.get("email"));
        MemberFormDTO MemberEmail = memberService.findMemberByEmail(member.get("email").toString());
        // MemberFormDTO -> Member로 변환 = entityToDto
        Long findMember = memberServiceImpl.dtoToEntity(MemberEmail).getId();
        List<CategoryDTO> categories = categoryService.categories(findMember);
        return categories;
    }

    // 카테고리 추가
    @PostMapping("/category")
    public Map<String, String> addCategory(@RequestBody CategoryDTO categoryDTO, @RequestHeader("Authorization") String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) {
            log.error("User is not authenticated");
            throw new IllegalArgumentException("User is not authenticated");
        }
        Map<String, Object> member = JWTUtil.validateToken(auth.substring(7));
        log.info("Authenticated user's emailByCategory: {}", member.get("email"));
        String MemberEmail = memberService.findMemberByEmail(member.get("email").toString()).getEmail();
        // MemberFormDTO -> Member로 변환 = entityToDto
        log.info("******** TodoController POST /category - categoryDTO : {}", categoryDTO);
        categoryService.addCategory(categoryDTO, MemberEmail);
        return Map.of("Result", "SUCCESS");
    }

    // 카테고리 수정
    @PutMapping("/category/{categoryName}")
    public Map<String, String> modifyCategory(
            @PathVariable("categoryName") String categoryName,
            @RequestBody CategoryDTO categoryDTO,
            @RequestHeader("Authorization") String auth) {
        log.info("******** TodoController PUT /category/{category} - categoryName : {}", categoryName);
        log.info("******** TodoController PUT /category/{category} - todoDTO : {}", categoryDTO);
        if (auth == null || !auth.startsWith("Bearer ")) {
            log.error("User is not authenticated");
            throw new IllegalArgumentException("User is not authenticated");
        }
        Map<String, Object> member = JWTUtil.validateToken(auth.substring(7));
        log.info("Authenticated user's emailByCategoryName: {}", member.get("email"));
        String MemberEmail = memberService.findMemberByEmail(member.get("email").toString()).getEmail();
        categoryDTO.setCategoryName(categoryName); // todoDTO에 path variable로 꺼낸 category 추가
        categoryService.modifyCategory(categoryDTO, MemberEmail);
        return Map.of("Result", "SUCCESS");
    }

    // 카테고리 삭제
    @DeleteMapping("/delCategory/{cno}")
    public Map<String, String> removeCategory(@PathVariable("cno") Long cno, @RequestHeader("Authorization") String auth) {
        if (auth == null || !auth.startsWith("Bearer ")) {
            log.error("User is not authenticated");
            throw new IllegalArgumentException("User is not authenticated");
        }
        Map<String, Object> member = JWTUtil.validateToken(auth.substring(7));
        log.info("Authenticated user's emailByCategoryRemove: {}", member.get("email"));
        String MemberEmail = memberService.findMemberByEmail(member.get("email").toString()).getEmail();
        categoryService.deleteCategory(cno, MemberEmail);
        return Map.of("Result", "SUCCESS");
    }

    /*
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
