package com.kh.ecolog.promise.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.ecolog.promise.model.dto.PromiseDTO;
import com.kh.ecolog.promise.model.service.PromiseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("promise")
public class PromiseController {
	
	private final PromiseService promiseService;
	
	// 첫등록
	@PostMapping
	public ResponseEntity<?> insert(@Valid @RequestBody PromiseDTO promise){
		promiseService.insert(promise);
		return ResponseEntity.ok().build();
	}
	
	// 수정
	@PutMapping("/me")
	public ResponseEntity<?> updateMyPromise(@RequestBody PromiseDTO promise){
		promiseService.updateMyPromise(promise);
		return ResponseEntity.ok("나의 다짐 수정 완료");
	}
	
	// 조회
	@GetMapping("/me")
	public ResponseEntity<PromiseDTO> selectMyPromise() {
	    PromiseDTO myPromise = promiseService.selectMyPromise();
	    return ResponseEntity.ok(myPromise);
	}
	
}
