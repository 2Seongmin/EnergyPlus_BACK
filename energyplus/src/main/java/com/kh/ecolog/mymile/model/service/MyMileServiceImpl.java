package com.kh.ecolog.mymile.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.kh.ecolog.auth.model.vo.CustomUserDetails;
import com.kh.ecolog.auth.service.AuthService;
import com.kh.ecolog.mymile.model.dao.MyMileMapper;
import com.kh.ecolog.mymile.model.dto.MyMileDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyMileServiceImpl implements MyMileService {
	
	private final AuthService authService;
	private final MyMileMapper myMileMapper;

	@Override
	public Map<String, Object> selectAll(int pageNo, String keyword) {
		CustomUserDetails user = authService.getUserDetails();
		Long userId = user.getUserId();
		
		int size = 5;
		RowBounds rowBounds = new RowBounds(pageNo * size, size);
		
		List<MyMileDTO> list;
		int totalCount;
		
		if (keyword == null || keyword.trim().isEmpty()) {
			list = myMileMapper.selectAll(userId, rowBounds);
			totalCount = myMileMapper.countAll(userId);
		} else {
			keyword = keyword.trim();
			Map<String, Object> param = new HashMap();
			param.put("userId", userId);
			param.put("keyword", keyword);
	    	
	        list = myMileMapper.searchMile(param, rowBounds);
	        totalCount = myMileMapper.countSearch(param);
		}
		Map<String, Object> result = new HashMap();
		result.put("list", list);
		result.put("totalCount", totalCount);
		return result;
	}

	@Override
	public void deleteById(Long mileId) {
		myMileMapper.deleteById(mileId);
	}

}
