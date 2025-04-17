package com.kh.ecolog.market.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.ecolog.market.model.dto.MarketDTO;
import com.kh.ecolog.market.model.dto.MarketImageDTO;

public interface MarketService {

	void insertMarket(MarketDTO dto, List<MultipartFile> images);

	List<MarketDTO> findAllMarkets();
	
	void updateMarket(MarketDTO dto, List<MultipartFile> images);

	void deleteMarket(Long marketNo);
	
}
