package com.kh.ecolog.admin.cardnews.model.service;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.ecolog.admin.cardnews.model.dao.CardNewsMapper;
import com.kh.ecolog.admin.cardnews.model.dto.CardNewsDTO;
import com.kh.ecolog.admin.cardnews.model.vo.CardNews;
import com.kh.ecolog.auth.model.vo.CustomUserDetails;
import com.kh.ecolog.auth.util.SecurityUtil;
import com.kh.ecolog.file.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardNewsServiceImpl implements CardNewsService {
	private final CardNewsMapper cardNewsMapper;
	private final FileService fileService;
	@Override
	public void insertCardNews(CardNewsDTO cardNewsDTO, MultipartFile file) {
	    
		Long userId = SecurityUtil.getCurrentUserId();
	    CardNews requestData; 
	    
	    if (file != null && !file.isEmpty()) {
	        String imgUrl = fileService.store(file);
	        requestData = CardNews.builder()
	                .cardNewsTitle(cardNewsDTO.getCardNewsTitle())
	                .cardNewsContent(cardNewsDTO.getCardNewsContent())
	                .cardNewsImgUrl(imgUrl)
	                .userId(userId)
	                .cardNewsDate(new Date(System.currentTimeMillis()))
	                .build();
	    } else {
	        requestData = CardNews.builder()
	                .cardNewsTitle(cardNewsDTO.getCardNewsTitle())
	                .cardNewsContent(cardNewsDTO.getCardNewsContent())
	                .userId(userId)
	                .cardNewsDate(new Date(System.currentTimeMillis()))
	                .build();
	    }

	    cardNewsMapper.insertCardNews(requestData);

	}
	
	@Override
	public List<CardNewsDTO> findAll(int pageNo) {
	    int size = 6; // 한 페이지당 6개
	    RowBounds rowBounds = new RowBounds(pageNo * size, size);
	    return cardNewsMapper.selectAllCardNews(rowBounds);
	}

	@Override
	public CardNewsDTO findById(Long id) {
		 return cardNewsMapper.selectCardNewsByNo(id);
	}

	 @Override
	 public List<CardNewsDTO> mainCardNews() {
		log.info("✅ CardNewsService.mainCardNews 실행됨");
	    return cardNewsMapper.mainCardNews();
	 }
	 
	 @Override
	 public void updateCardNews(Long id, CardNewsDTO cardNewsDTO, MultipartFile file) {
	     CardNewsDTO existing = cardNewsMapper.selectCardNewsByNo(id);
	     if (existing == null) {
	         throw new RuntimeException("해당 카드뉴스가 존재하지 않습니다.");
	     }

	     Long userId = SecurityUtil.getCurrentUserId();

	     String newImgUrl = existing.getCardNewsImgUrl();

	     if (file != null && !file.isEmpty()) {
	         if (newImgUrl != null) {
	             fileService.delete(newImgUrl);
	         }
	         newImgUrl = fileService.store(file);
	     }

	     cardNewsDTO.setCardNewsNo(id);
	     cardNewsDTO.setCardNewsImgUrl(newImgUrl);
	     cardNewsDTO.setUserId(userId);
	     cardNewsDTO.setCardNewsDate(new Date(System.currentTimeMillis()));

	     cardNewsMapper.updateCardNews(cardNewsDTO);
	 }
	 
	 @Override
	 public void deleteCardNews(Long id) {
	     CardNewsDTO existing = cardNewsMapper.selectCardNewsByNo(id);
	     if (existing == null) {
	         throw new RuntimeException("해당 카드뉴스가 존재하지 않습니다.");
	     }

	     if (existing.getCardNewsImgUrl() != null) {
	         fileService.delete(existing.getCardNewsImgUrl());
	     }

	     cardNewsMapper.deleteCardNews(id);
	 }

}
