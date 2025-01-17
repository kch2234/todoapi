package com.cicd.todoapi.repository;

import com.cicd.todoapi.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional // EntityManager 사용위해
@Rollback(value = false)
// 테스트에서는 자동롤백이 되므로, DB에 수정결과 유지하기 위해 롤백안되게 설정
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ValueRepository valueRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EntityManager em;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsert() {
        for (int i = 1; i <= 5; i++) {
            Member member = memberRepository.findById(1L).orElse(null);

            Value value = Value.builder().valueString("value" + i).member(member).build();
            valueRepository.save(value);

            Category category = Category.builder().categoryName("category" + i).member(member).build();
            categoryRepository.save(category);

            Todo todo = Todo.builder()
                    .member(member)
                    .title("Todo " + i)
                    .dueDate(LocalDate.of(2024, 12, 31))
                    .value(value)
                    .category(category)
                    .priority("중간")
                    .build();
            todoRepository.save(todo);
        }
    }

    // 투두 조회
    @Test
    public void testSelect() {
        Long tno = 10L;
        Todo todo = todoRepository.findById(tno).orElseThrow();
        log.info("todo: {}", todo);
    }

    // 투두 수정
    @Test
    public void testModify() {
        Long tno = 10L;
        Todo findTodo = todoRepository.findById(tno).orElseThrow(); // -> 1차 캐시
        findTodo.changeTitle("Modified 10....");
        findTodo.changeComplete(true);
        findTodo.changeDueDate(LocalDate.of(2025, 2, 3));
        em.flush(); // 쿼리문 내보내기
        em.clear(); // 1차 캐시 비우고
        // 다시 DB에서 조회 -> 1차 캐시로 저장
        Todo modifiedTodo = todoRepository.findById(tno).orElseThrow();
        log.info("modified Todo : {}", modifiedTodo);
    }

    // 투두 삭제
    @Test
    public void deleteTest() {
        Long tno = 13L;
        // delete 사용
        Todo findTodo = todoRepository.findById(tno).orElse(null);
        if(findTodo != null) {
            todoRepository.delete(findTodo);
            log.info("삭제 완료");
        }else {
            log.info("tno {}번은 없습니다. 삭제 실패...", tno); // 직접 예외 처리 가능
        }
        /*
        // deleteById 사용시 코드 간단 -> 예외처리 고정됨
        todoRepository.deleteById(tno); -> select + delete 두개 실행
        */
    }

    // 투두 리스트 조회
    @Test
    public void listTest() {
        Long memberId = 1L;
        Member member = memberRepository.findById(memberId).orElse(null);
        log.info("member: {}", member);
        // memberId로 투두 리스트 조회
        List<Todo> todoList = todoRepository.findAllByMemberId(memberId,
                Sort.by(Sort.Direction.DESC, "id"));
        log.info("todoList: {}", todoList);
    }
}