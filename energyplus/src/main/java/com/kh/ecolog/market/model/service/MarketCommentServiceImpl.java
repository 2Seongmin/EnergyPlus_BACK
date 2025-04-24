package com.kh.ecolog.market.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.ecolog.market.model.dao.MarketCommentMapper;
import com.kh.ecolog.market.model.dto.MarketCommentDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketCommentServiceImpl implements MarketCommentService  {
	
	private final MarketCommentMapper commentMapper;

	@Override
	public void insertComment(MarketCommentDTO dto) {
		commentMapper.insertComment(dto);
		
	}

	@Override
	public List<MarketCommentDTO> selectCommentsByMarketNo(Long marketNo) {
		return commentMapper.selectCommentsByMarketNo(marketNo);
	}
	
	@Override
	public void updateComment(MarketCommentDTO dto) {
		commentMapper.updateComment(dto);
		
	}
	
	@Override
	public void deleteComment(Long commentNo, Long userId) {
		commentMapper.deleteComment(commentNo);
		
	}

	
}
