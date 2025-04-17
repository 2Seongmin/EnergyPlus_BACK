package com.kh.ecolog.api.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.ecolog.api.model.dto.ZerostoreDTO;

@Mapper
public interface ZerostoreMapper {

	List<ZerostoreDTO> requestGetZerostore();
	
}
