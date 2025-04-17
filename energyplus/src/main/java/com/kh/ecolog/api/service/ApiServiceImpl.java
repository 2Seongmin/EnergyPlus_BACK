package com.kh.ecolog.api.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kh.ecolog.api.model.dao.ZerostoreMapper;
import com.kh.ecolog.api.model.dto.ZerostoreDTO;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
	
	private final String SERVICE_KEY = "716a75484c6c736d37375768416176";
	
	private String apiRequest(String uriPath) {
		URI uri = null;
		
		try {
			uri = new URI(uriPath);			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		String apiResponseData = new RestTemplate().getForObject(uri, String.class);
		return apiResponseData;
	}

	@Override
	public String requestGetPublicBicycle() {
		
		StringBuilder sb = new StringBuilder("http://openapi.seoul.go.kr:8088");
		sb.append("/" + SERVICE_KEY);
		sb.append("/json/tbCycleStationInfo/1/100");
		
		return apiRequest(sb.toString());
	}

	// --------------------------------------------------- //
	private final ZerostoreMapper zerostoreMapper;
	
	@Override
	public List<ZerostoreDTO> requestGetZerostore() {
		return zerostoreMapper.requestGetZerostore();
	}


	

}
