package com.kh.ecolog.mileage.model.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.ecolog.file.service.FileService;
import com.kh.ecolog.mileage.model.dao.MileageMapper;
import com.kh.ecolog.mileage.model.dto.MileageDTO;
import com.kh.ecolog.mileage.model.vo.Mileage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MileageServiceImpl implements MileageService {
	
	private final MileageMapper mileageMapper;
	private final FileService fileService;
	
	@Override
	public void saveMileage(MileageDTO mileage, MultipartFile file) {

		Mileage requestData = null;
		
		if(file != null & !file.isEmpty()) {
			
			String filePath = fileService.store(file);
			
			log.info("파일 저장 완료 : {}", filePath);
			
			requestData = Mileage.builder()
					.mileageTitle(mileage.getMileageTitle())
					.mileageCategory(mileage.getMileageCategory())
					.mileageContent(mileage.getMileageContent())
					.mileageImg(filePath)
					.build();
			
		} else {
			
			requestData = Mileage.builder()
					.mileageTitle(mileage.getMileageTitle())
					.mileageCategory(mileage.getMileageCategory())
					.mileageContent(mileage.getMileageContent())
					.build();
		}
		
		mileageMapper.saveMileage(requestData);
		
	}

	
	
}
