package com.kh.ecolog.info.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.ecolog.info.model.dto.InfoDTO;
import com.kh.ecolog.info.model.service.InfoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/info")
@RequiredArgsConstructor
public class InfoController {
	
	private final InfoService infoService;
	
	// 내 정보 조회
	@GetMapping
	public ResponseEntity<InfoDTO> selectMyInfo() {
		InfoDTO info = infoService.selectMyInfo();
		return ResponseEntity.ok(info);
	}
	
	// 내 정보 수정
	@PutMapping
	public ResponseEntity<?> updateMyInfo(@Valid @RequestBody InfoDTO info){
		infoService.updateMyInfo(info);
		return ResponseEntity.ok("내 정보 수정 완료");
	}

}
