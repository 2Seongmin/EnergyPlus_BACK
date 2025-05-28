package com.kh.ecolog.api.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kh.ecolog.api.model.dao.CarbonMapper;
import com.kh.ecolog.api.model.dao.ZerostoreMapper;
import com.kh.ecolog.api.model.dto.CarbonDTO;
import com.kh.ecolog.api.model.dto.ZerostoreDTO;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
	
	@Value("${api.public.service-key}")
	private String serviceKey;
	
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
		sb.append("/" + serviceKey);
		sb.append("/json/tbCycleStationInfo/1/100");
		
		return apiRequest(sb.toString());
	}

	// --------------------------------------------------- //
	private final ZerostoreMapper zerostoreMapper;
	
	@Override
	public List<ZerostoreDTO> requestGetZerostore() {
		return zerostoreMapper.requestGetZerostore();
	}
	
	
	// -------------------------------------------------- //
	/* 메인 대시보드 활용 API */
	
	@Value("${api.public.service-key2}")
	private String mainServiceKey;
	
	@Override
	public String requestEnergyUsage1(int pageNo) {
		
		StringBuilder sb = new StringBuilder("http://apis.data.go.kr/B553530/GHG_LIST_03/GHG_LIST_03_02_VIEW");
		sb.append("?serviceKey=" + URLEncoder.encode(mainServiceKey, StandardCharsets.UTF_8));
		sb.append("&numOfRows=100");
		sb.append("&apiType=JSON");
		sb.append("&pageNo=")
				.append(pageNo);
		
		return apiRequest(sb.toString());
	}

	@Override
	public String requestEnergyUsage2(int pageNo) {
		
		StringBuilder sb = new StringBuilder("http://apis.data.go.kr/B553530/GHG_LIST_03/GHG_LIST_03_03_VIEW");
		sb.append("?serviceKey=" + URLEncoder.encode(mainServiceKey, StandardCharsets.UTF_8));
		sb.append("&numOfRows=100");
		sb.append("&apiType=JSON");
		sb.append("&pageNo=")
				.append(pageNo);
		
		return apiRequest(sb.toString());
	}
	
	// ----------------------------------------------------------- //
	/* Carbon 실제 데이터 활용 */
	private final CarbonMapper carbonMapper;
	
	@Override
	public List<CarbonDTO> requestGetCarbon() {
		return carbonMapper.requestGetCarbon();
	}
	

}