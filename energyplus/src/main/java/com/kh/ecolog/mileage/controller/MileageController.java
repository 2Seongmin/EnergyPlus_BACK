package com.kh.ecolog.mileage.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.ecolog.auth.model.vo.CustomUserDetails;
import com.kh.ecolog.mileage.model.dto.MileageDTO;
import com.kh.ecolog.mileage.model.dto.MileageStoreDTO;
import com.kh.ecolog.mileage.model.service.MileageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/mileages")
@CrossOrigin(origins="http://localhost:5173")
public class MileageController {

	private final MileageService mileageService;
	
	/* 마일리지 신청글 저장 */
	@PostMapping("/save")
	public ResponseEntity<?> saveMileage(@ModelAttribute MileageDTO mileage, 
										 @RequestParam(name="mileageImg", required=false) MultipartFile file){
		
		log.info("인증 신청글 정보 : {}, 파일 정보 : {}", mileage, file);
		return mileageService.saveMileage(mileage, file);
	}
	
	
	/* 마일리지 사용처 */
	 @GetMapping("/stores")
	    public List<MileageStoreDTO> getAllStores() {
	        return mileageService.findAllStores();
	    }
}