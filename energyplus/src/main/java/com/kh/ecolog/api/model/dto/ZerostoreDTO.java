package com.kh.ecolog.api.model.dto;

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
public class ZerostoreDTO {

	private Long zerostoreSeq;
	private String storeName;
	private int lat;
	private int lot;
	
}
