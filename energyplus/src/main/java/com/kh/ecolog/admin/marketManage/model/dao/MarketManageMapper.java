package com.kh.ecolog.admin.marketManage.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.ecolog.market.board.model.dto.MarketDTO;

@Mapper
public interface MarketManageMapper {
	
	List<MarketDTO> findAllMarketsForAdmin();
	MarketDTO findMarketByNo(Long marketNo);
}
