package com.kh.ecolog.auth.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kh.ecolog.auth.model.vo.CustomUserDetails;
import com.kh.ecolog.exception.UserNotFoundException;
import com.kh.ecolog.member.model.dao.MemberMapper;
import com.kh.ecolog.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
	
    private final MemberMapper memberMapper;
    
    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    
    MemberDTO user = memberMapper.getMemberByMemberEmail(username); // 실제로는 userEmail
    
    if (user == null) {
        log.warn("사용자를 찾을 수 없음: {}", username);
        throw new UserNotFoundException("존재하지 않는 사용자입니다.");
    }
    
    // CustomUserDetails 객체 생성 및 반환
    return CustomUserDetails.builder()
            .userId(user.getUserId())
            .username(user.getUserEmail())
            .password(user.getUserPassword())
            .name(user.getUserName())
            .authorities(Collections.singletonList(new SimpleGrantedAuthority(user.getRole())))
            .build();
}

}
