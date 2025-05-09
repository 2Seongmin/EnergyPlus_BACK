package com.kh.ecolog.market.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.ecolog.market.board.model.dto.MarketDTO;

public interface MarketService {

	void insertMarket(MarketDTO dto, List<MultipartFile> images);


	
	void updateMarket(MarketDTO dto, List<MultipartFile> images);

	void deleteMarket(Long marketNo, Long userId);
	
	void deleteImagesByMarketNo(Long marketNo);
	
	List<MarketDTO> findAllMarkets();
	
	Map<String, Object> searchMarkets(int pageNo, String keyword);

	MarketDTO findMarketByNo(Long marketNo);
	
	// 관리자용 신고 처리 로직
	void hideMarket(Long marketNo);

	
}
