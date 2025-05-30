package com.kh.ecolog.admin.mileageManage.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.kh.ecolog.admin.mileageManage.model.dto.MileageManageDTO;
import com.kh.ecolog.admin.mileageManage.model.service.MileageManageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/admin/mileages")
@CrossOrigin(origins="http://localhost:5173")
public class MileageManageController {

	private final MileageManageService mileageManageService;
	
	@GetMapping
	public ResponseEntity<List<MileageManageDTO>> findAllMileage(@RequestParam(name="page", defaultValue="0") int page){
		
		return ResponseEntity.ok(mileageManageService.findAllMileage(page));
	}
	
	
	@GetMapping("/{mileageSeq}")
	public ResponseEntity<MileageManageDTO> getMileageDetail(@PathVariable("mileageSeq") Long mileageSeq){
		
		MileageManageDTO mileage = mileageManageService.detailMileage(mileageSeq);
		
	    return ResponseEntity.ok(mileage);
	}


	@PostMapping("/{mileageSeq}/status")
	public ResponseEntity<?> updateMileageStatusS(
			@PathVariable("mileageSeq") Long mileageSeq,
			@RequestBody MileageManageDTO mileageDTO) {

		mileageDTO.setMileageSeq(mileageSeq);
		mileageManageService.updateMileageStatusS(mileageDTO);
		return ResponseEntity.ok("상태와 점수가 업데이트되었습니다.");
	}

	@PostMapping("/{mileageSeq}/statusReject")
	public ResponseEntity<?> updateMileageStatusR(
			@PathVariable("mileageSeq") Long mileageSeq,
			@RequestBody MileageManageDTO mileageDTO) {

		mileageDTO.setMileageSeq(mileageSeq);
		mileageManageService.updateMileageStatusR(mileageDTO);
		return ResponseEntity.ok("상태와 점수가 업데이트되었습니다.");
	}


}
