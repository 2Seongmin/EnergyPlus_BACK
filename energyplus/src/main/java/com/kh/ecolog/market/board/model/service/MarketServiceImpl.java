package com.kh.ecolog.market.board.model.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.ecolog.auth.util.SecurityUtil;
import com.kh.ecolog.file.service.FileService;
import com.kh.ecolog.market.board.model.dao.MarketMapper;
import com.kh.ecolog.market.board.model.dto.MarketDTO;
import com.kh.ecolog.market.board.model.dto.MarketImageDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MarketServiceImpl implements MarketService  {
	private final MarketMapper marketMapper;
	private final FileService fileService;
	
	@Override
	public void insertMarket(MarketDTO dto, List<MultipartFile> images) {
		
		Long userId = SecurityUtil.getCurrentUserId();
		 dto.setUserId(userId);
	    
	    dto.setMarketStatus("N");
	    dto.setMarketDate(new Date(System.currentTimeMillis()));
	    images.get(0);
	    marketMapper.insertMarket(dto);
	    handleImages(dto.getMarketNo(), images, false);
	}
	
	private void saveMarketImages(Long marketNo, List<MultipartFile> images) {
		
	    if (images == null || images.size() != 3) {
	        throw new IllegalArgumentException("이미지는 3장 등록해야 합니다.");
	    }

	    AtomicInteger order = new AtomicInteger(1);

	    images.forEach(file -> {
	        String url = fileService.store(file);

	        MarketImageDTO img = MarketImageDTO.builder()
	                .marketNo(marketNo)
	                .imgUrl(url)
	                .imgOrder(order.getAndIncrement())
	                .build();

	        marketMapper.insertMarketImage(img);
	    });
	}
	
	
	private void checkUserAuthorization(Long writerUserId) {
	    Long currentUserId = SecurityUtil.getCurrentUserId();
	    if (writerUserId == null) {
	        throw new RuntimeException("작성자가 존재하지 않습니다.");
	    }
	    if (!writerUserId.equals(currentUserId)) {
	        throw new RuntimeException("권한이 없습니다.");
	    }
	}
	
	@Override
	public void updateMarket(MarketDTO dto, List<MultipartFile> images) {
	    // 작성자 확인
	    Long writerUserId = marketMapper.findMarketWriter(dto.getMarketNo());
	    checkUserAuthorization(writerUserId);

	    // 1. 먼저 게시글의 기존 이미지를 전부 삭제
	    marketMapper.deleteImagesByMarketNo(dto.getMarketNo());

	    // 2. 최종 이미지 3개를 순서대로 저장
	    List<String> keepUrls = dto.getKeepImageUrls() != null ? dto.getKeepImageUrls() : new ArrayList<>();
	    List<MultipartFile> validImages = (images != null) ? images.stream().filter(img -> img != null && !img.isEmpty()).toList() : new ArrayList<>();

	    // 총 3장 검사
	    int totalCount = keepUrls.size() + validImages.size();
	    if (totalCount != 3) {
	        throw new IllegalArgumentException("수정 시 이미지는 총 3장이 있어야 합니다");
	    }

	    AtomicInteger order = new AtomicInteger(1);
	    System.out.println(keepUrls);
	    // 기존 이미지 URL 저장
	    for (String url : keepUrls) {
	        MarketImageDTO image = MarketImageDTO.builder()
	                .marketNo(dto.getMarketNo())
	                .imgUrl(url)
	                .imgOrder(order.getAndIncrement())
	                .build();
	        marketMapper.insertMarketImage(image);
	    }

	    // 새로 업로드된 파일 저장
	    for (MultipartFile file : validImages) {
	        try {
	            String url = fileService.store(file);
	            MarketImageDTO image = MarketImageDTO.builder()
	                    .marketNo(dto.getMarketNo())
	                    .imgUrl(url)
	                    .imgOrder(order.getAndIncrement())
	                    .build();
	            marketMapper.insertMarketImage(image);
	        } catch (Exception e) {
	            System.out.println("이미지 저장 실패: " + file.getOriginalFilename());
	            e.printStackTrace();
	            throw new RuntimeException("이미지 업로드 중 오류 발생", e);
	        }
	    }
	    System.out.println("먼디");

	    // 3. 게시글 내용 업데이트
	    marketMapper.updateMarket(dto);
	}


	private void handleImages(Long marketNo, List<MultipartFile> images, boolean isUpdate) {
	    if (isUpdate) {
	        // 기존 이미지 무조건 삭제
	        marketMapper.deleteImagesByMarketNo(marketNo);
	    }

	    if (images != null && !images.isEmpty()) {
	        saveMarketImages(marketNo, images);
	    }
	}
	
	@Override
	public void deleteImagesByMarketNo(Long marketNo) {
	    marketMapper.deleteImagesByMarketNo(marketNo);
	}
	
	@Override
	public void deleteMarket(Long marketNo) {
	    Long writerUserId = marketMapper.findMarketWriter(marketNo);
	    checkUserAuthorization(writerUserId);
	    marketMapper.deleteImagesByMarketNo(marketNo);
	    marketMapper.deleteMarket(marketNo);
	}
	@Override
	public List<MarketDTO> findAllMarkets() {
	    return marketMapper.findAllMarkets();
	}

	@Override
	public MarketDTO findMarketByNo(Long marketNo) {
	    MarketDTO dto = marketMapper.selectMarketByNo(marketNo);
	    dto.setImageList(marketMapper.selectImagesByMarketNo(marketNo));

	    Long currentUserId = null;
	    try {
	        currentUserId = SecurityUtil.getCurrentUserId();
	    } catch (Exception e) {
	        // 로그인 안 한 경우 null 유지
	    }

	    if (currentUserId != null && dto.getUserId().equals(currentUserId)) {
	        dto.setIsMine(true);
	    } else {
	        dto.setIsMine(false);
	    }

	    return dto;
	}

	 @Override
	    public Map<String, Object> searchMarkets(int pageNo, String keyword) {
	        int pageSize = 12;
	        int offset = pageNo * pageSize;

	        Map<String, Object> param = new HashMap<>();
	        param.put("keyword", keyword);

	        RowBounds rowBounds = new RowBounds(offset, pageSize);
	        List<MarketDTO> list = marketMapper.searchMarket(param, rowBounds);
	        int totalCount = marketMapper.countMarketSearch(param);

	        Map<String, Object> result = new HashMap<>();
	        result.put("list", list);
	        result.put("totalCount", totalCount);
	        return result;
	    }
	
//	 관리자용 신고 로직
	 @Override
	 public void hideMarket(Long marketNo) {
		    marketMapper.hideMarket(marketNo, "Y");
	}
	
}