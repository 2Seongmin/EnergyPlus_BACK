package com.kh.ecolog.admin.marketManage.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReportManageDTO {
	  private Long reportId;
	  private String marketTitle;
	  private String reportReason;
	  private Date reportDate;
	  private Long marketNo;
	  private int totalReportCount;
	  private String marketContent;
	  private String isHidden;
}
