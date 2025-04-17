package com.kh.ecolog.mileage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.ecolog.mileage.model.dto.MileageDTO;
import com.kh.ecolog.mileage.model.service.MileageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/mileages")
public class MileageController {

	private final MileageService mileageService;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveMileage(@ModelAttribute MileageDTO mileage, @RequestParam(name="mileageImg", required=false) MultipartFile file){
		
		log.info("게시글 정보 : {}, 파일 정보 : {}", mileage, file);
		mileageService.saveMileage(mileage, file);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
		
	}
	
}
