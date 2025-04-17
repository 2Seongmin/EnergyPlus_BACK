package com.kh.ecolog.notice.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Notice {

	Long noticeId;
	String noticeTitle;
	String noticeContent;
	Date noticeDate;
	Long userId;
}
