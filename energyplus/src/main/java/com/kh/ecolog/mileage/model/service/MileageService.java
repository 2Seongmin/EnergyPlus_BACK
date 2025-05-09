package com.kh.ecolog.mileage.model.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.kh.ecolog.mileage.model.dto.MileageDTO;
import com.kh.ecolog.mileage.model.dto.MileageStoreDTO;

public interface MileageService {

	// 마일리지 적립 신청 게시글 작성
	// (제목, 카테고리, 설명, 인증사진(multipartFile)) 입력 
	public ResponseEntity<?> saveMileage(MileageDTO mileage, MultipartFile file);
	
	
	/* 마일리지 사용처 */
	List<MileageStoreDTO> findAllStores();
	
}