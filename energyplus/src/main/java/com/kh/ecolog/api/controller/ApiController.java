package com.kh.ecolog.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
	
	@GetMapping("/energyUsage1")
<<<<<<< HEAD
	public ResponseEntity<String> getenergyUsage1(@RequestParam(name = "pageNo") int pageNo){
=======
	public ResponseEntity<String> getenergyUsage1(@RequestParam(name="pageNo") int pageNo){
>>>>>>> db8fbbe41c837f35d52665baa9f222717c0b04ae
		String responseData = apiService.requestEnergyUsage1(pageNo);
		return ResponseEntity.ok(responseData);
	}
	
	@GetMapping("/energyUsage2")
<<<<<<< HEAD
	public ResponseEntity<String> getenergyUsage2(@RequestParam(name = "pageNo") int pageNo){
=======
	public ResponseEntity<String> getenergyUsage2(@RequestParam(name="pageNo") int pageNo){
>>>>>>> db8fbbe41c837f35d52665baa9f222717c0b04ae
		String responseData = apiService.requestEnergyUsage2(pageNo);
		return ResponseEntity.ok(responseData);
	}
}