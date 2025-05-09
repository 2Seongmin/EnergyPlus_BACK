package com.kh.ecolog.member.model.service;

import com.kh.ecolog.member.model.dto.MemberDTO;

public interface MemberService {
	
	/**
	 * 회원가입 메서드
	 * @param memberDTO
	 */
	void signUp(MemberDTO memberDTO);
	
	/**
	 * 이메일 중복 확인 메서드
	 * @param email
	 * @return 중복 여부(t->중복, f -> 중복 아님)
	 */
	boolean isEmailDuplicated(String email);

	/**
	 * 회원 탈퇴 메소드
	 * @param userId 탈퇴할 회원의 식별자(시퀀스넘버)
	 * @return 탈퇴 결과(t -> 성공, f -> 실패)
	 */
	boolean withdrawMember(Long userId);
	
	/**
	 * 회원 식별자(usrId)로 회원정보 조회
	 * @param userId 조회할 회원의 시퀀스넘버
	 * @return 조회할 회원의 정보
	 */
	MemberDTO getMemberByUserId(Long userId);
	
	
	/**
	 * 비밀번호 재설정 메서드
	 * @param email
	 * @param newPassword
	 */
	void resetPassword(String email, String newPassword);
	
    /**
     * 마케팅 정보 수신 동의 상태 업데이트
     * @param userId 회원 ID
     * @param marketingAgreed 마케팅 동의 여부
     * @return 업데이트 성공 여부
     */
    boolean updateMarketingAgreed(Long userId, boolean marketingAgreed);
}

