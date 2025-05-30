package com.kh.ecolog.info.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.ecolog.auth.model.vo.CustomUserDetails;
import com.kh.ecolog.auth.service.AuthService;
import com.kh.ecolog.info.model.dao.InfoMapper;
import com.kh.ecolog.info.model.dto.InfoDTO;
import com.kh.ecolog.info.model.dto.PasswordDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class InfoServiceImpl implements InfoService {
	
	private final InfoMapper infoMapper;
	private final AuthService authService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public InfoDTO selectMyInfo() {
		InfoDTO info = infoMapper.selectMyInfo(getCurrentUserId());
		
		if(info == null) {
			throw new RuntimeException("내 정보가 없습니다.");
		}
		return info;
	}

	@Override
	public void updateMyInfo(InfoDTO info) {
		info.setUserId(getCurrentUserId());
		infoMapper.updateMyInfo(info);
	}
	
	// 비밀번호 수정
	@Override
	public void changePassword(PasswordDTO changePassword) {
		String encodePassword = passwordEncoder.encode(changePassword.getNewPassword());
		Long userId = passwordMatches(changePassword.getCurrentPassword());
		
		Map<String, Object> changeRequest = new HashMap();
		changeRequest.put("userId", userId);
		changeRequest.put("encodePassword", encodePassword);
		
		infoMapper.changePassword(changeRequest);
	}
	
	private Long passwordMatches(String password) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
		
		if(!passwordEncoder.matches(password, user.getPassword())) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}
		return user.getUserId();
	}
	
	// 내 등급 조회
	@Override
	public InfoDTO selectMyGrade() {
		return infoMapper.selectMyGrade(getCurrentUserId());
	}
	
	// userId
	private Long getCurrentUserId() {
		return authService.getUserDetails().getUserId();
	}

}
