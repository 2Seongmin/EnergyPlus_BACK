package com.kh.ecolog.member.model.dto;

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
public class MarketingAgreementDTO {
	
	private Long agreementId;
	private Long userId;
	private String marketingAgreed; // Y또눈N
	private String agreementDate;

}
