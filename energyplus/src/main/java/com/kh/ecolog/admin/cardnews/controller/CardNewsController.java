package com.kh.ecolog.admin.cardnews.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kh.ecolog.admin.cardnews.model.dto.CardNewsDTO;
import com.kh.ecolog.admin.cardnews.model.service.CardNewsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin/cardnews")
@RequiredArgsConstructor
@Slf4j
public class CardNewsController {
	
	private final CardNewsService cardNewsService;
	
	@PostMapping("/form")
	public ResponseEntity<?> save(@Valid CardNewsDTO cardNewsDTO,
								  @RequestParam(name="file", required = false)MultipartFile file) {
		cardNewsService.insertCardNews(cardNewsDTO, file);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<CardNewsDTO>> findAll(@RequestParam(name = "page", defaultValue = "0" ) int page ) {
		return ResponseEntity.ok(cardNewsService.findAll(page));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CardNewsDTO> getCardNewsDetail(@PathVariable("id") Long id) {
	    CardNewsDTO response = cardNewsService.findById(id);
	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/main")
	public ResponseEntity<List<CardNewsDTO>> getMainCardNews() {
	    log.info("✅ 카드뉴스 메인 요청 들어옴");
	    List<CardNewsDTO> cardNewsList = cardNewsService.mainCardNews();
	    log.info("✅ 받아온 카드뉴스 개수: {}", cardNewsList.size());
	    return ResponseEntity.ok(cardNewsList);
	}

	@PutMapping("/edit/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id,
	                                @Valid CardNewsDTO cardNewsDTO,
	                                @RequestParam(name="file", required = false) MultipartFile file) {
	    cardNewsService.updateCardNews(id, cardNewsDTO, file);
	    return ResponseEntity.ok().build();
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
	    cardNewsService.deleteCardNews(id);
	    return ResponseEntity.ok().build();
	}
	

}
