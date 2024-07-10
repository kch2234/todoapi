package com.cicd.todoapi.controller;

import com.cicd.todoapi.domain.Member;
import com.cicd.todoapi.dto.MemberFormDTO;
import com.cicd.todoapi.dto.TodoDTO;
import com.cicd.todoapi.service.MemberService;
import com.cicd.todoapi.service.MemberServiceImpl;
import com.cicd.todoapi.service.TodoService;
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



    /*
    // Todo 한개 조회
    @GetMapping("/{tno}")
    public TodoDTO get(@PathVariable("tno") Long tno) {
        log.info("******** TodoController GET /:tno - tno : {}", tno);
        TodoDTO todoDTO = todoService.get(tno);
        return todoDTO;
    }
    */

/*    // Todo 목록 조회 + 페이징처리
    @GetMapping("/list")
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {
        log.info("******* TodoController GET /list - pageRequestDTO : {}", pageRequestDTO);
        PageResponseDTO<TodoDTO> list = todoService.list(pageRequestDTO);
        return list;
    }*/
}
