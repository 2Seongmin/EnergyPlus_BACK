package com.kh.ecolog.admin.marketManage.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.ecolog.admin.marketManage.model.service.MarketManageService;
import com.kh.ecolog.market.board.model.dto.MarketDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/market")
@RequiredArgsConstructor
public class MarketManageController {

    private final MarketManageService marketManageService;

    @GetMapping("/list")
    public ResponseEntity<List<MarketDTO>> getAllMarkets() {
        List<MarketDTO> list = marketManageService.findAllMarketsForAdmin();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/detail/{marketNo}")
    public ResponseEntity<MarketDTO> getMarketDetail(@PathVariable("marketNo") Long marketNo) {
        MarketDTO market = marketManageService.findMarketByNo(marketNo);
        return ResponseEntity.ok(market);
    }
    @PutMapping("/hide/{marketNo}")
    public ResponseEntity<?> toggleHideMarket(@PathVariable("marketNo") Long marketNo) {
        marketManageService.toggleMarketHidden(marketNo);
        return ResponseEntity.ok().build();
    }
}
