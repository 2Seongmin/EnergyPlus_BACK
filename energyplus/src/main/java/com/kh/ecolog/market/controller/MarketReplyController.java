package com.kh.ecolog.market.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.ecolog.market.model.dto.MarketDTO;
import com.kh.ecolog.market.model.dto.MarketReplyDTO;
import com.kh.ecolog.market.model.service.MarketReplyService;

@RestController
@RequestMapping("/markets/reply")
public class MarketReplyController {

	private final MarketReplyService marketReplyService;
	
	public MarketReplyController(MarketReplyService marketReplyService) {
	   this.marketReplyService = marketReplyService;
	   // 
	}
	 
	@PostMapping("/write")
	 public ResponseEntity<String> insertMarketReply(@RequestBody MarketReplyDTO dto) {
		
	    marketReplyService.insertMarketReply(dto);

	    return ResponseEntity.ok("답댓글 등록 성공!");
	  }
	@GetMapping("{marketCommentNo}")
	public ResponseEntity<List<MarketReplyDTO>> getReplies(@PathVariable("marketCommentNo") Long marketCommentNo) {
		List<MarketReplyDTO> replies = marketReplyService.selectRepliesByCommentNo(marketCommentNo);
		return ResponseEntity.ok(replies);
	}
}
