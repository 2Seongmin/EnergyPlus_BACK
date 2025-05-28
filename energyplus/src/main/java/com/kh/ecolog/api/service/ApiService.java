package com.kh.ecolog.api.service;

import java.util.List;

import com.kh.ecolog.api.model.dto.CarbonDTO;
import com.kh.ecolog.api.model.dto.ZerostoreDTO;

public interface ApiService {

	String requestGetPublicBicycle();
	
	List<ZerostoreDTO> requestGetZerostore();
	
	String requestEnergyUsage1(int pageNo);
	
	String requestEnergyUsage2(int pageNo);
	
	List<CarbonDTO> requestGetCarbon();
}
