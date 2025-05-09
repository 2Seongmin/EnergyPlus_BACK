package com.kh.ecolog.member.model.service;


import java.beans.Transient;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.ecolog.exception.MemberEmailDuplicateException;
import com.kh.ecolog.exception.UserNotFoundException;
import com.kh.ecolog.member.model.dao.MemberMapper;
import com.kh.ecolog.member.model.dto.MarketingAgreementDTO;
import com.kh.ecolog.member.model.dto.MemberDTO;
import com.kh.ecolog.member.model.vo.Member;
import com.kh.ecolog.token.model.service.TokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder; // 비밀번호, 전화번호 암호화를 위한 의존성 주입
	private final TokenService tokenService;

	@Override
	public void signUp(MemberDTO member) {
		
		// 같은 이메일이 있는지 유효성 검사
		if(isEmailDuplicated(member.getUserEmail())){
			throw new MemberEmailDuplicateException("이미 존재하는 이메일입니다.");
		}

		// 이메일 중복x
		Member memberValue = Member.builder()
								   .gradeId(member.getGradeId())
								   .userEmail(member.getUserEmail())
								   .userPassword(passwordEncoder.encode(member.getUserPassword()))
								   .userName(member.getUserName())
								   .userPhone(member.getUserPhone() != null ? passwordEncoder.encode(member.getUserPhone()) : null)
								   .role("ROLE_USER")
								   .build();
		
		memberMapper.signUp(memberValue);
		
//	    // 저장된 회원 정보 조회
//	    MemberDTO savedMember = memberMapper.getMemberByMemberEmail(member.getUserEmail());
//	    
//	    // 마케팅 동의 정보가 있는 경우에만 저장
//	    if (member.getMarketingAgreed() != null && member.getMarketingAgreed()) {
//	        MarketingAgreementDTO agreementDTO = new MarketingAgreementDTO();
//	        agreementDTO.setUserId(savedMember.getUserId());
//	        agreementDTO.setMarketingAgreed("Y");
//	        
//	        memberMapper.saveMarketingAgreement(agreementDTO);
//	    }
		
	}

	@Override
	public boolean isEmailDuplicated(String email) {
		
		return memberMapper.existsByEmail(email)>0;
	}
	
	@Override
	@Transactional
	public boolean withdrawMember(Long userId) {
		
		MemberDTO member = memberMapper.getMemberByUserId(userId);
		if(member == null) {
			throw new UserNotFoundException("존재하지 않는 사용자입니다,");
		}
		
		// 회원이 진짜 있는게 맞다면 -> 회원 상태를 'N'으로 변경
		int result = memberMapper.withdrawMember(userId); 
		tokenService.deleteUserToken(userId);
		log.info("회원탈퇴 완료 : 사용자 ID = {}",userId);
		
		return result > 0;
	}
	
	@Override
	public MemberDTO getMemberByUserId(Long userId) {
		MemberDTO member = memberMapper.getMemberByUserId(userId);
		if(member ==null) {
			throw new UserNotFoundException("존재하지 않는 사용자입니다.");
		}
		
		// 존재하면
		return member;
	}
	
	@Override
	public void resetPassword(String email, String newPassword) {
		
		// 사용자존재 여부 확인
		MemberDTO member = memberMapper.getMemberByMemberEmail(email);
		
		// 사용자가 없다면
		if(member == null) {
			throw new RuntimeException("존재하지 않는 사용자입니다.");
		}
		
		// 비밀번호 변경
		String encodedNewPassword = passwordEncoder.encode(newPassword);
		int result = memberMapper.updatePassword(email, encodedNewPassword);

		if (result != 1) {
			log.error("비밀번호 업데이트 실패: 이메일 = {}", email);
			throw new RuntimeException("비밀번호 업데이트에 실패했습니다.");
		}
		
		log.info("비밀번호 재설정 완료: 이메일 = {}", email);
		
		
	}
	
    @Override
    @Transactional
    public boolean updateMarketingAgreed(Long userId, boolean marketingAgreed) {
        String marketingAgreedValue = marketingAgreed ? "Y" : "N";
        int result = memberMapper.updateMarketingAgreed(userId, marketingAgreedValue);
        return result > 0;
    }

}