package com.cicd.todoapi.security;

import com.cicd.todoapi.domain.Member;
import com.cicd.todoapi.dto.MemberUserDetail;
import com.cicd.todoapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // email 로 회원 조회 -> MemberDTO(UserDetails 타입)으로 변환 후 리턴
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("***** CustomUserDetailsService/loadUserByUsername - username : {}", username);
        Member member = memberRepository.getMemberByEmail(username);
        log.info("***** CustomUserDetailsService/loadUserByUsername - username : {}", member.getNickname());
        if (member == null) { // 없는 사용자(email)일 경우 예외 발생
            throw new UsernameNotFoundException("****** CustomUserDetailsService - loadUserByUsername : Email(username) Not Found");
        }

//    List<ProfileImageDTO> profileImageDTOList = new ArrayList<>();  // 프로필 이미지 리스트

        MemberUserDetail userDetail = new MemberUserDetail(member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getPassword(),
                member.getRole());
//    MemberUserDetail userDetail = modelMapper.map(member, MemberUserDetail.class);

        log.info("***** CustomUserDetailsService/loadUserByUsername - memberDTO : {}", userDetail);

        return userDetail;
//    return null;

    }

}
