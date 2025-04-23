package com.kh.ecolog.qna.model.dto;

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
public class QnaDTO {
	private Long qnaId;
	private Long userId;
	private String qnaTitle;
	private String qnaContent;
	private Date qnaDate;
	private String qnaStatus;
}
