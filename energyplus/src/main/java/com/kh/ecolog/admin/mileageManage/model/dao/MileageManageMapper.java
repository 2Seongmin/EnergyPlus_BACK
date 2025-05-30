package com.kh.ecolog.admin.mileageManage.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.kh.ecolog.admin.mileageManage.model.dto.MileageManageDTO;


@Mapper
public interface MileageManageMapper {
	
	List<MileageManageDTO> findAllMileage(RowBounds rb);
	
	MileageManageDTO detailMileage(@Param("mileageSeq") Long mileageSeq);

	void updateMileageStatusS(MileageManageDTO dto);

	void updateMileageStatusR(MileageManageDTO dto);

}