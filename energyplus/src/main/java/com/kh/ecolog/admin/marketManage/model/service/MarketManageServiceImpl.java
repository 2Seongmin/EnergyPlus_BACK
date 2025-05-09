package com.kh.ecolog.admin.marketManage.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.ecolog.admin.marketManage.model.dao.MarketManageMapper;
import com.kh.ecolog.market.board.model.dao.MarketMapper;
import com.kh.ecolog.market.board.model.dto.MarketDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketManageServiceImpl implements MarketManageService {
	
	 private final MarketManageMapper marketManageMapper;
	 private final MarketMapper marketMapper; 
	 
	 public List<MarketDTO> findAllMarketsForAdmin() {
		    return marketManageMapper.findAllMarketsForAdmin();
	 }
	 @Override
	 public MarketDTO findMarketByNo(Long marketNo) {
	     MarketDTO dto = marketManageMapper.findMarketByNo(marketNo);
	     dto.setImageList(marketMapper.selectImagesByMarketNo(marketNo));
	     return dto;
	 }
	 @Override
	 public void toggleMarketHidden(Long marketNo) {
	     MarketDTO market = marketManageMapper.findMarketByNo(marketNo);
	     String currentStatus = market.getIsHidden();
	     String newStatus = currentStatus.equals("Y") ? "N" : "Y";
	     marketMapper.hideMarket(marketNo, newStatus);
	 }
}
