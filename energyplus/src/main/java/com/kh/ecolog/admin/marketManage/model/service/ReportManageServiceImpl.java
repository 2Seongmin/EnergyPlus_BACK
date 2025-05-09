package com.kh.ecolog.admin.marketManage.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.ecolog.admin.marketManage.model.dao.ReportManageMapper;
import com.kh.ecolog.admin.marketManage.model.dto.ReportManageDTO;
import com.kh.ecolog.market.board.model.service.MarketService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportManageServiceImpl implements ReportManageService {

    private final ReportManageMapper reportManageMapper;

    @Override
    public List<ReportManageDTO> findAllReports() {
        return reportManageMapper.findAllReports();
    }
    @Override
    public ReportManageDTO findById(Long reportId) {
        ReportManageDTO dto = reportManageMapper.selectById(reportId);
        if (dto == null) {
            throw new RuntimeException("해당 신고가 존재하지 않습니다.");
        }
        return dto;
    }
    @Override
    public void markReportAsHidden(Long reportId) {
        reportManageMapper.markMarketAsHidden(reportId);
    }
   
}
