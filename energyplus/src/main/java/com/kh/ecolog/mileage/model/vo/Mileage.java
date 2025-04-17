package com.kh.ecolog.mileage.model.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Mileage {

	private Long mileageSeq;
	private String userId;
	private String mileageTitle;
	private String mileageContent;
	private String mileageCategory;
	private String mileageImg;
	private String mileageStatus;
	private Integer mileageScore;
	
}
