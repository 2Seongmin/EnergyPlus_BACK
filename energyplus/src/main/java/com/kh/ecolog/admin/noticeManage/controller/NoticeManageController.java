package com.kh.ecolog.admin.noticeManage.controller;

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

import com.kh.ecolog.admin.noticeManage.model.dto.NoticeManageDTO;
import com.kh.ecolog.admin.noticeManage.model.service.NoticeManageService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/admin/notices")
public class NoticeManageController {
	
	private final NoticeManageService noticeManageService;

	// 공지 등록
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody NoticeManageDTO noticeManage) {
		noticeManageService.save(noticeManage);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	// 전체 공지 목록 (+ 페이징)
	@GetMapping
	public ResponseEntity<List<NoticeManageDTO>> findAll(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "keyword", required = false) String keyword){
		return ResponseEntity.ok(noticeManageService.findAll(page, keyword));
	}

	// 특정 공지 상세 보기
	@GetMapping("/{id}")
	public ResponseEntity<NoticeManageDTO> findById(@PathVariable("id") @Min(1) Long noticeId) {
		return ResponseEntity.ok(noticeManageService.findById(noticeId));
	}

	// 공지 수정
	@PutMapping("/{id}")
	public ResponseEntity<NoticeManageDTO> update(@PathVariable("id") Long noticeId,
											@RequestBody NoticeManageDTO notice) {
		notice.setNoticeId(noticeId);
		return ResponseEntity.ok(noticeManageService.update(notice));
	}

	// 공지 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long noticeId) {
		noticeManageService.deleteById(noticeId);
		return ResponseEntity.ok().build();
	}

	// 페이지 수 조회 
	@GetMapping("/pages")
	public ResponseEntity<Integer> getPageCount(@RequestParam(name = "keyword", required = false) String keyword) {
		return ResponseEntity.ok(noticeManageService.getTotalPages(keyword));
	}


}
