package com.kh.ecolog.market.reply.model.service;

import java.util.List;

import com.kh.ecolog.market.reply.model.dto.MarketReplyDTO;


public interface MarketReplyService {
	
	// 답댓글 등록 메서드 
	void insertMarketReply(MarketReplyDTO dto);
	
	List<MarketReplyDTO> selectRepliesByCommentNo(Long marketCommentNo);
	
	void updateMarketReply(MarketReplyDTO dto);
	
	void deleteMarketReply(MarketReplyDTO dto);

}
