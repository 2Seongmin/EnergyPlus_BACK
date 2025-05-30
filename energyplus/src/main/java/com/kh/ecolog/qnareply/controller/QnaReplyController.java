package com.kh.ecolog.qnareply.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.ecolog.qnareply.model.dto.QnaReplyDTO;
import com.kh.ecolog.qnareply.model.service.QnaReplyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/replys")
@RequiredArgsConstructor
public class QnaReplyController {
	
	private final QnaReplyService qnaReplyService;
	
	// 댓글 등록
	@PostMapping
	public ResponseEntity<?> insertReply(@Valid @RequestBody QnaReplyDTO reply){
		qnaReplyService.insertReply(reply);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	// 댓글 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(name="id") Long replyId){
		qnaReplyService.deleteById(replyId);
		return ResponseEntity.ok().build();
	}
	
	// 댓글 수정
	@PutMapping("/{id}")
	public ResponseEntity<QnaReplyDTO> update(
									@PathVariable(name="id") Long replyId,
									@RequestBody QnaReplyDTO reply) {
		reply.setQnaReplyId(replyId);
		return ResponseEntity.status(HttpStatus.CREATED).body(qnaReplyService.update(reply));
	}
	
	// 댓글 조회
	@GetMapping
	public ResponseEntity<List<QnaReplyDTO>> selectReplyList(@RequestParam(name="qnaId") Long qnaId){
		List<QnaReplyDTO> reply = qnaReplyService.selectReplyList(qnaId);
		return reply.isEmpty() ? ResponseEntity.noContent().build() // 댓글 없을 때
							   : ResponseEntity.ok(reply); // 댓글 있을 때
	}
	
}
