package com.kh.ecolog.qna.model.vo;

import java.sql.Date;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Qna {
	private long qnaId;
	private long userId;
	private String qnaTitle;
	private String qnaContent;
	private Date qnaDate;
	private String qnaStatus;
	private String qnaReply;
	private Date qnaReplyDate;
}
