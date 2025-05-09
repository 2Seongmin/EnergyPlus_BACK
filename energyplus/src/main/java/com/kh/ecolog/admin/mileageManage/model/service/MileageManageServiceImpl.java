package com.kh.ecolog.admin.mileageManage.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.kh.ecolog.admin.mileageManage.model.dao.MileageManageMapper;
import com.kh.ecolog.auth.service.AuthService;
import com.kh.ecolog.file.service.FileService;
import com.kh.ecolog.mileage.model.dao.MileageMapper;
import com.kh.ecolog.mileage.model.dto.MileageDTO;
import com.kh.ecolog.mileage.model.service.MileageServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MileageManageServiceImpl implements MileageManageService {
	
	private final MileageManageMapper mileageManageMapper;

	// 관리자 권한
	@Override
	public List<MileageDTO> findAllMileage(int page) {
		int size = 10;
		RowBounds rowBounds = new RowBounds(page * size, size);
			
		return mileageManageMapper.findAllMileage(rowBounds);
	}
		
	@Override
	public MileageDTO detailMileage(Long mileageSeq) {
			
		return mileageManageMapper.detailMileage(mileageSeq);
	}


	/* 마일리지 승인(적립) */
	@Override
	public void updateMileageStatusS(MileageDTO dto) {
		dto.setMileageStatus("S");

		if (dto.getMileageScore() == 0) {
			System.out.println("포인트 값이 0입니다.");
		}

		mileageManageMapper.updateMileageStatusS(dto);
	}

	/* 마일리지 거부(적립 X) */
	@Override
	public void updateMileageStatusR(MileageDTO dto) {
		dto.setMileageStatus("R");
		mileageManageMapper.updateMileageStatusR(dto);
	}

}

