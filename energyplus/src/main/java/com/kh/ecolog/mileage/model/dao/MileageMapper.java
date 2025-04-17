package com.kh.ecolog.mileage.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.ecolog.mileage.model.vo.Mileage;


@Mapper
public interface MileageMapper {
	
	void saveMileage(Mileage mileage);

}
