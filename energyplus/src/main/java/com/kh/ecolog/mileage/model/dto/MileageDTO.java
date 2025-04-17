package com.kh.ecolog.mileage.model.dto;

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
	private String userId;
	private String mileageTitle;
	private String mileageContent;
	private String mileageCategory;
	private String mileageStatus;
	private Integer mileageScore;
	
}