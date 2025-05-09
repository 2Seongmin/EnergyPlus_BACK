package com.kh.ecolog.market.board.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.kh.ecolog.market.board.model.dto.MarketDTO;
import com.kh.ecolog.market.board.model.dto.MarketImageDTO;
import com.kh.ecolog.qna.model.dto.QnaDTO;


@Mapper
public interface MarketMapper {
	void insertMarket(MarketDTO dto);
	List<MarketDTO> findAllMarkets();
	void insertMarketImage(MarketImageDTO image);
	void updateMarket(MarketDTO dto);
	void deleteImagesByMarketNo(Long marketNo);
	void deleteMarket(Long marketNo);
	MarketDTO selectMarketByNo(Long marketNo);
	List<MarketImageDTO> selectImagesByMarketNo(Long marketNo);
	Long findMarketWriter(Long marketNo);
	void deleteImageByUrl(String imgUrl);
	List<String> findAllImageUrls(Long marketNo);
	
	// 검색
	int countMarketAll(Long userId); 
	List<MarketDTO> searchMarket(Map<String, Object> param, RowBounds rowBounds);
	int countMarketSearch(Map<String, Object> param);
	
	// 관리자 신고글 숨김 처리
	void hideMarket(@Param("marketNo") Long marketNo, @Param("hidden") String hidden);

}
