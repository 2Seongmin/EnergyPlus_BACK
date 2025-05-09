package com.kh.ecolog.admin.marketManage.model.service;

import com.kh.ecolog.admin.marketManage.model.dto.ReportManageDTO;
import java.util.List;

public interface ReportManageService {
    List<ReportManageDTO> findAllReports();
    ReportManageDTO findById(Long reportId);
    void markReportAsHidden(Long reportId);
    
}
