package com.kh.ecolog.mymarket.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.ecolog.auth.model.vo.CustomUserDetails;
import com.kh.ecolog.auth.service.AuthService;
import com.kh.ecolog.mymarket.model.dao.MyMarketMapper;
import com.kh.ecolog.mymarket.model.dto.MyMarketDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyMarketServiceImpl implements MyMarketService {
	
	private final MyMarketMapper myMarketMapper;
	private final AuthService authService;

	@Override
	public List<MyMarketDTO> selectMyMarket() {
		Long userId = authService.getUserDetails().getUserId();
		return myMarketMapper.selectMyMarket(userId);
	}

	@Override
	public MyMarketDTO findMarketByNo(Long marketNo) {
		MyMarketDTO dto = myMarketMapper.selectMarketByNo(marketNo);
		if(dto == null) {
			throw new RuntimeException("해당 게시글이 존재하지 않습니다. marketNo = " + marketNo);
		}
		dto.setImageList(myMarketMapper.selectImagesByMarketNo(marketNo));
	    return dto;
	}

}
