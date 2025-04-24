package com.kh.ecolog.market.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.ecolog.market.model.dto.MarketCommentDTO;

@Mapper
public interface MarketCommentMapper {
	
	void insertComment(MarketCommentDTO comment);
	
	List<MarketCommentDTO> selectCommentsByMarketNo(Long marketNo);
	
	void updateComment(MarketCommentDTO comment);
	
	void deleteComment(@Param("marketCommentNo") Long marketCommentNo);

	MarketCommentDTO selectCommentByNo(Long commentNo);
}
