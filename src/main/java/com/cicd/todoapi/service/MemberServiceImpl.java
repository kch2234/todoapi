package com.cicd.todoapi.service;

import com.cicd.todoapi.domain.Member;
import com.cicd.todoapi.domain.Role;
import com.cicd.todoapi.dto.MemberFormDTO;
import com.cicd.todoapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;

    @Override
    public Long signup(MemberFormDTO memberFormDTO) {
        log.info("********** MemberServiceImpl signup - memberFormDTO : {}", memberFormDTO);

        log.info("********** MemberServiceImpl signup - password : {}", memberFormDTO.getPassword());
        Member member = dtoToEntity(memberFormDTO);

        member.addRole(Role.USER);
        member.changePassword(encoder.encode(member.getPassword()));
        log.info("********** MemberServiceImpl signup - encodedPassword : {}", member.getPassword());
        Member saved = memberRepository.save(member);
        log.info("********** MemberServiceImpl signup - member : {}", member);
        return saved.getId();
    }

    @Override
    public Boolean checkEmail(String email) {
        boolean result = memberRepository.existsByEmail(email);
        log.info("***** MemberServiceImpl checkEmail - existsByEmail : {}", result);

        return result;
    }

    @Override
    public Boolean checkNickname(String nickname) {
        boolean result = memberRepository.existsByNickname(nickname);
        log.info("***** MemberServiceImpl checkNickname - checkNickname : {}", result);

        return result;
    }

    // 내부에서만 사용할 메서드 -> private 으로 지정
    // Entity -> MemberFormDTO
    private MemberFormDTO entityToDto(Member member) {
        MemberFormDTO memberFormDTO = MemberFormDTO.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .role(member.getRole())
                .build();
        return memberFormDTO;
    }

    // MemberFormDTO -> Entity
    private Member dtoToEntity(MemberFormDTO memberFormDTO) {
        Member member = Member.builder()
                .id(memberFormDTO.getId())
                .email(memberFormDTO.getEmail())
                .password(memberFormDTO.getPassword())
                .nickname(memberFormDTO.getNickname())
                .role(memberFormDTO.getRole())
                .build();
        return member;
    }
}