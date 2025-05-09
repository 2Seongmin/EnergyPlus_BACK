package com.kh.ecolog.admin.marketManage.model.service;

import java.util.List;

import com.kh.ecolog.market.board.model.dto.MarketDTO;


public interface MarketManageService {
	List<MarketDTO> findAllMarketsForAdmin();
	MarketDTO findMarketByNo(Long marketNo);
	void toggleMarketHidden(Long marketNo);
}
