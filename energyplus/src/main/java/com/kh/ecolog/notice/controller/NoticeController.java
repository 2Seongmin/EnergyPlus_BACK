package com.kh.ecolog.notice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.kh.ecolog.notice.model.dto.NoticeDTO;
import com.kh.ecolog.notice.model.service.NoticeService;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeController {

    private final NoticeService noticeService;

    /**
     * 전체 공지 목록 조회 (페이징 & 검색)
     */
    @GetMapping
    public ResponseEntity<List<NoticeDTO>> findAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "keyword", required = false) String keyword) {
        return ResponseEntity.ok(noticeService.findAll(page, keyword));
    }

    /**
     * 공지사항 상세 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<NoticeDTO> findById(@PathVariable("id") Long noticeId) {
        return ResponseEntity.ok(noticeService.findById(noticeId));
    }

    /**
     * 전체 페이지 수 반환 (검색 포함)
     */
    @GetMapping("/pages")
    public ResponseEntity<Integer> getPageCount(
            @RequestParam(name = "keyword", required = false) String keyword) {
        return ResponseEntity.ok(noticeService.getTotalPages(keyword));
    }
}
