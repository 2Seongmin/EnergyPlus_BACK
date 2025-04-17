package com.kh.ecolog.market.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.ecolog.market.model.dto.MarketDTO;
import com.kh.ecolog.market.model.dto.MarketImageDTO;

@Mapper
public interface MarketMapper {
	void insertMarket(MarketDTO dto);
	
	void update(MarketDTO market);
	
	List<MarketDTO> findAllMarkets();
	void updateMarket(MarketDTO dto);
	void insertMarketImage(MarketImageDTO img);
	void deleteImagesByMarketNo(Long marketNo);
	void deleteMarket(Long marketNo);
	void deleteMarketImagesByMarketNo(Long marketNo);
}
