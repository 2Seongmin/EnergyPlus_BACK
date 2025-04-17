package com.kh.ecolog.api.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.kh.ecolog.api.model.dto.ZerostoreDTO;
import com.kh.ecolog.api.service.ApiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins="http://localhost:5173")
@RequestMapping("apis")
@RequiredArgsConstructor
public class ApiController {
	
	private final ApiService apiService;

	@GetMapping("/bicycle")
	public ResponseEntity<String> getPublicBicycle(){
		String responseData = apiService.requestGetPublicBicycle();
		return ResponseEntity.ok(responseData);
	}
	
	@GetMapping("/container")
	public ResponseEntity<List<ZerostoreDTO>> getZerostoreList() {
		List<ZerostoreDTO> responseData = apiService.requestGetZerostore();
        return ResponseEntity.ok(responseData);
    }
	
}
