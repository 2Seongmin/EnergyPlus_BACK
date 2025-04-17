package com.kh.ecolog.mileage.model.service;

import org.springframework.web.multipart.MultipartFile;

import com.kh.ecolog.mileage.model.dto.MileageDTO;

public interface MileageService {

	// 마일리지 적립 신청 게시글 작성
	// (제목, 카테고리, 설명, 인증사진(multipartFile)) 입력 
	public void saveMileage(MileageDTO mileage, MultipartFile file);
	
	
	/* 관리자 권한 */
	// 작성된 인증 게시글의 상태를 변경 (UPDATE)
	
	
	
	
}
