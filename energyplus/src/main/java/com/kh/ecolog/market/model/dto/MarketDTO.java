package com.kh.ecolog.market.model.dto;

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
public class MarketDTO {
	private Long marketNo;
    private Long userId;
    private String userName;      
    private String marketTitle;
    private String marketContent;
    private Date marketDate;
    private String marketStatus;
    private Long marketPrice;
    private String thumbnailUrl;
    private String marketStatusLabel;
}
