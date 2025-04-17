package com.kh.ecolog.notice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.ecolog.notice.model.dto.NoticeDTO;
import com.kh.ecolog.notice.model.service.NoticeService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/notices")
@CrossOrigin(origins = "http://localhost:5173")
public class NoticeController {
	
	private final NoticeService noticeService;

	// 공지 등록
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody NoticeDTO notice) {
		noticeService.save(notice);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	// 전체 공지 목록 (페이징)
	@GetMapping
	public ResponseEntity<List<NoticeDTO>> findAll(@RequestParam(name = "page", defaultValue = "0") int page) {
		return ResponseEntity.ok(noticeService.findAll(page));
	}

	// 특정 공지 상세 보기
	@GetMapping("/{id}")
	public ResponseEntity<NoticeDTO> findById(@PathVariable("id") @Min(1) Long noticeId) {
		return ResponseEntity.ok(noticeService.findById(noticeId));
	}

	// 공지 수정
	@PutMapping("/{id}")
	public ResponseEntity<NoticeDTO> update(@PathVariable("id") Long noticeId,
											@RequestBody NoticeDTO notice) {
		notice.setNoticeId(noticeId);
		return ResponseEntity.ok(noticeService.update(notice));
	}

	// 공지 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long noticeId) {
		noticeService.deleteById(noticeId);
		return ResponseEntity.ok().build();
	}
	

}
