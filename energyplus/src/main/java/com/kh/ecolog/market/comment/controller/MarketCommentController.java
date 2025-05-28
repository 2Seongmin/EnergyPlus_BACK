package com.kh.ecolog.market.comment.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
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

import com.kh.ecolog.market.comment.model.dto.MarketCommentDTO;
import com.kh.ecolog.market.comment.model.service.MarketCommentService;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/markets/comments")
public class MarketCommentController {
	private final MarketCommentService commentService;
	
	@PostMapping
	public ResponseEntity<?> insertComment(@RequestBody MarketCommentDTO dto) {
		commentService.insertComment(dto);
		return ResponseEntity.ok("댓글 등록 완료");
	}
	
	@GetMapping("/{marketNo}")
	public ResponseEntity<List<MarketCommentDTO>> getComments(@PathVariable("marketNo") Long marketNo) {
	    log.info("marketNo = {}", marketNo);  // 이거 찍히는지 확인
	    List<MarketCommentDTO> comments = commentService.selectCommentsByMarketNo(marketNo);
	    return ResponseEntity.ok(comments);
	}
	
	@PutMapping
	public ResponseEntity<?> updateComment(@RequestBody MarketCommentDTO dto) {
		commentService.updateComment(dto);
		return ResponseEntity.ok("댓글 수정 완료");
	}
	
	@DeleteMapping("/{commentNo}")
	public ResponseEntity<String> deleteComment(@PathVariable("commentNo") Long commentNo) {
	    commentService.deleteCommentAndReplies(commentNo);
	    return ResponseEntity.ok("댓글 + 대댓글 + 신고 삭제 완료");
	}

    @DeleteMapping("/delete/{commentNo}")
    public ResponseEntity<Void> deleteCommentWithReplies(@PathVariable Long commentNo) {
    	commentService.deleteCommentAndReplies(commentNo);
        return ResponseEntity.ok().build();
    }
	


}
