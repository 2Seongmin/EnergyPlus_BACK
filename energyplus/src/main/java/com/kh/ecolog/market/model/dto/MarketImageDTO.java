package com.kh.ecolog.market.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MarketImageDTO {
	 	private Long marketImgId;   
	    private Long marketNo;       
	    private String imgUrl;     
	    private int imgOrder;        
}
