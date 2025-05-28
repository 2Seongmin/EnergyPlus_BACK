package com.kh.ecolog.mymile.model.dto;

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
public class TotalMileDTO {
	private Long userId;
	private String mileageStatus;
	private int totalScore;
}
