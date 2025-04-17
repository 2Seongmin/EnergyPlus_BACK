package com.kh.ecolog.market.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.ecolog.market.model.dao.MarketMapper;
import com.kh.ecolog.market.model.dto.MarketDTO;
import com.kh.ecolog.market.model.dto.MarketImageDTO;
import com.kh.ecolog.market.model.service.MarketService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/markets")
@CrossOrigin(origins = "http://localhost:5173")
public class MarketController {
	private final MarketService marketService; 
	
	private final MarketMapper marketMapper;
	
	  @GetMapping("/test-insert")
	    public void testInsertImage() {
	        MarketImageDTO img = MarketImageDTO.builder()
	            .marketNo(24L)
	            .imgUrl("test.jpg")
	            .imgOrder(1)
	            .build();

	        marketMapper.insertMarketImage(img);
	    }
	
	@PostMapping("/write")
	public String insertMarket(@ModelAttribute MarketDTO dto,
								@RequestParam("images") List<MultipartFile> images) {
		if(images == null || images.size() != 3) {
			return "이미지는 정확히 3장 등록해야합니다.";
		}
		
		marketService.insertMarket(dto, images);
		return "글 등록 성공!";
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateMarket(
		  @RequestPart("market") MarketDTO dto,
		  @RequestPart(value = "images", required = false) List<MultipartFile> images
	) {
		  marketService.updateMarket(dto, images);
		  return ResponseEntity.ok("수정 성공!");
	}
	
	@DeleteMapping("/delete/{marketNo}")
	public ResponseEntity<String> deleteMarket(@PathVariable("marketNo") Long marketNo) {
	    marketService.deleteMarket(marketNo);
	    return ResponseEntity.ok("삭제 성공!");
	}
	
//	게시글 조회
	
	 @GetMapping
	    public List<MarketDTO> findAllMarkets() {
	        return marketService.findAllMarkets(); // JSON 반환
	    }
	 
	
}
