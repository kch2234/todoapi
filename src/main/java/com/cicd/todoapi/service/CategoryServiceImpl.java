package com.cicd.todoapi.service;

import com.cicd.todoapi.domain.Category;
import com.cicd.todoapi.domain.Member;
import com.cicd.todoapi.dto.CategoryDTO;
import com.cicd.todoapi.repository.CategoryRepository;
import com.cicd.todoapi.repository.MemberRepository;
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

    private final CategoryRepository categoryRepository;
    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CategoryDTO> categories(Long findMember) {
        List<Category> categories = categoryRepository.findMemberIdByCategory(findMember);
        log.info("categories: {}", categories);
        List<CategoryDTO> collect = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public Long addCategory(CategoryDTO categoryDTO, String memberEmail) {
        Member member = memberRepository.getMemberByEmail(memberEmail);

        Category category = Category.builder()
                .cno(categoryDTO.getCno())
                .member(member)
                .categoryName(categoryDTO.getCategoryName())
                .build();
        Category save = categoryRepository.save(category);
        CategoryDTO saved = modelMapper.map(save, CategoryDTO.class);
        return saved.getCno();
    }

    @Override
    public void modifyCategory(CategoryDTO categoryDTO, String memberEmail) {
        Member member = memberRepository.getMemberByEmail(memberEmail);

        Category category = categoryRepository.findById(categoryDTO.getCno())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + categoryDTO.getCno()));

        if (!category.getMember().equals(member)) {
            throw new IllegalArgumentException("Category not found with id: " + categoryDTO.getCno());
        }

        category.changeCategoryName(categoryDTO.getCategoryName());
    }

    @Override
    public void deleteCategory(Long cno, String memberEmail) {
        Member member = memberRepository.getMemberByEmail(memberEmail);

        Category category = categoryRepository.findById(cno).orElse(null);
        if (category != null && category.getMember().equals(member)) {
            categoryRepository.delete(category);
        }else {
            log.info("삭제 실패.....");
        }
    }
}
