package com.kh.ecolog.qna.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.ecolog.qna.model.dto.QnaDTO;
import com.kh.ecolog.qna.model.service.QnaService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/qnas")
public class QnaController {
	
	private final QnaService qnaService;
	
	// 등록
	@PostMapping
	public ResponseEntity<?> insert(@Valid @RequestBody QnaDTO qna){
		qnaService.insert(qna);
		return ResponseEntity.ok().build();
	}
	
	// 전체 조회
	@GetMapping
	public ResponseEntity<Map<String, Object>> selectAll(
			@RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name = "keyword", required = false) String keyword){
		return ResponseEntity.ok(qnaService.selectAll(page, keyword));
	}
	
	// 1개 조회
	@GetMapping("/{id}")
	public ResponseEntity<QnaDTO> selectById(
			@PathVariable(name="id")
			@Min(value=1, message="너무 작음") Long qnaId){
		return ResponseEntity.ok(qnaService.selectById(qnaId));
	}
	
	// 수정
	@PutMapping("/{id}")
	public ResponseEntity<QnaDTO> update(
								@PathVariable(name="id") Long qnaId,
								@RequestBody QnaDTO qna){
		qna.setQnaId(qnaId);
		return ResponseEntity.status(HttpStatus.CREATED).body(qnaService.update(qna));
	}
	
	// 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(name="id") Long qnaId){
		qnaService.deleteById(qnaId);
		return ResponseEntity.ok().build();
	}
	

}
