package com.kh.ecolog.market.board.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.ecolog.market.board.model.dto.MarketDTO;
import com.kh.ecolog.market.board.model.service.MarketService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/markets")
public class MarketController {
	private final MarketService marketService; 


	@PostMapping("/write")
	public ResponseEntity<?> insertMarket(
			
		    @RequestParam("marketTitle") String marketTitle,
		    @RequestParam("marketContent") String marketContent,
		    @RequestParam("marketPrice") Long marketPrice,
		    @RequestParam(name = "images") MultipartFile[] images) {
		
		   //Long userId = userIdFromToken(authHeader);
		
		   MarketDTO dto = new MarketDTO();
		    dto.setMarketTitle(marketTitle);
		    dto.setMarketContent(marketContent);
		    dto.setMarketPrice(marketPrice);
		    List<MultipartFile> imgs = Arrays.asList(images);
		    marketService.insertMarket(dto, imgs);

		    Map<String, Object> res = new HashMap<>();
		    res.put("message", "글 등록 성공!");
		    res.put("marketNo", dto.getMarketNo());
		    return ResponseEntity.ok(res);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateMarket(
	    @RequestHeader("Authorization") String authHeader,
	    @ModelAttribute MarketDTO dto,
	    @RequestParam(value = "keepImageUrls", required = false) List<String> keepImageUrls,
	    @RequestParam(value = "images", required = false) List<MultipartFile> images
	) {
		log.info("{}", keepImageUrls);
		log.info("{}", images);
	    dto.setKeepImageUrls(keepImageUrls);
	    marketService.updateMarket(dto, images);
	    return ResponseEntity.ok("수정 성공!");
	}
	// 게시글 삭제
	@DeleteMapping("/delete/{marketNo}")
	public ResponseEntity<String> deleteMarket(
	    @RequestHeader("Authorization") String authHeader,
	    @PathVariable("marketNo") Long marketNo
	) {
	    marketService.deleteMarket(marketNo); 
	    return ResponseEntity.ok("삭제 성공!");
	}
	//	게시글 조회
	@GetMapping
	public ResponseEntity<List<MarketDTO>> getAllMarkets() {
	    List<MarketDTO> list = marketService.findAllMarkets();
	    return ResponseEntity.ok(list);
	}
	// 게시글 상세 조회
	@GetMapping("/{marketNo}")
	public ResponseEntity<MarketDTO> getMarket(@PathVariable("marketNo") Long marketNo) {
		MarketDTO dto = marketService.findMarketByNo(marketNo);
		return ResponseEntity.ok(dto);
	}
	// 검색 + 페이징 용 
	@GetMapping("/search")
	public ResponseEntity<Map<String, Object>> searchMarkets(
	        @RequestParam(name = "page", defaultValue = "0") int page,
	        @RequestParam(name = "keyword", required = false) String keyword
	) {
	    return ResponseEntity.ok(marketService.searchMarkets(page, keyword));
	}
	

	
}