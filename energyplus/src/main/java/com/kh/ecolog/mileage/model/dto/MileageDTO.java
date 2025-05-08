package com.kh.ecolog.mileage.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class MileageDTO {
	
	private Long mileageSeq;
	private Long userId;
	private String mileageTitle;
	private String mileageContent;
	private String mileageCategory;
	private String mileageStatus;
	private int mileageScore;
	private String mileageImg;
	private String mileageReject;
	private Date createDate;
	private String userName;
}