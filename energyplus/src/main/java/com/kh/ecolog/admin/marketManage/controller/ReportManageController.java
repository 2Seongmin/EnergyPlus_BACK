package com.kh.ecolog.admin.marketManage.controller;
import com.kh.ecolog.admin.marketManage.model.dto.ReportManageDTO;
import com.kh.ecolog.admin.marketManage.model.service.ReportManageService;
import com.kh.ecolog.market.board.model.service.MarketService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/market/report")
@RequiredArgsConstructor
public class ReportManageController {

    private final ReportManageService reportManageService;
    private final MarketService marketService;

    // 전체 신고 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<ReportManageDTO>> findAllReports() {
    	System.out.println(123);
        List<ReportManageDTO> list = reportManageService.findAllReports();
        return ResponseEntity.ok(list);
    }
    
    // 2. 신고 상세 조회
    @GetMapping("/{reportId}")
    public ResponseEntity<ReportManageDTO> findReportById(@PathVariable("reportId") Long reportId) {
        ReportManageDTO report = reportManageService.findById(reportId);
        return ResponseEntity.ok(report);
    }
    // 3. 
    @PutMapping("/hide/{reportId}/{marketNo}")
    public ResponseEntity<?> hideMarket(
            @PathVariable("reportId") Long reportId,
            @PathVariable("marketNo") Long marketNo) {

        marketService.hideMarket(marketNo);
        return ResponseEntity.ok().build();
    }


}
