package com.kh.ecolog.admin.mileageManage.model.service;

import java.util.List;

import com.kh.ecolog.mileage.model.dto.MileageDTO;

public interface MileageManageService {
	
	// 마일리지 신청 리스트 조회
	public abstract List<MileageDTO> findAllMileage(int page);
	
	// 마일리지 신청 글 조회
	public MileageDTO detailMileage(Long mileageSeq);
	
	// 마일리지 승인(적립)
	public void updateMileageStatusS(MileageDTO dto);
	
	// 마일리지 거부(적립 X)
	public void updateMileageStatusR(MileageDTO dto);

}