package com.cicd.todoapi.service;

import com.cicd.todoapi.domain.Member;
import com.cicd.todoapi.domain.Role;
import com.cicd.todoapi.dto.MemberFormDTO;
import com.cicd.todoapi.dto.MemberModifyDTO;
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

    @Override
    public MemberFormDTO getMember(String email) {
        Member result = memberRepository.getMemberByEmail(email);
        return entityToDto(result);
    }

    @Override
    public void modifyMember(MemberModifyDTO memberModifyDTO) {
        Member member = memberRepository.getMemberByEmail(memberModifyDTO.getEmail());
        log.info("***** MemberServiceImpl modifyMember - member(email) : {}", member);

        if (memberModifyDTO.getNickname() != null) {
            member.changeNickname(memberModifyDTO.getNickname());
        }
        if (memberModifyDTO.getPassword() != null) {
            member.changePassword(encoder.encode(memberModifyDTO.getPassword()));
        }
        log.info("***** MemberServiceImpl modifyMember - member : {}", member);
        memberRepository.save(member);
    }

    @Override
    public MemberFormDTO findMemberByEmail(String email) {
        log.info("********** MemberServiceImpl findMemberByEmail - email : {}", email);
        Member member = memberRepository.getMemberByEmail(email);
        log.info("********** MemberServiceImpl findMemberByEmail - member : {}", member);
        MemberFormDTO memberFormDTO = entityToDto(member);
        return memberFormDTO;
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

    // 로그인 email 받아서 TodoController에서 사용 하기 위해
    // todoDTO.SetMember에 저장하기 위해 public으로 변경
    public Member dtoToEntity(MemberFormDTO memberFormDTO) {
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