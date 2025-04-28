package com.kh.ecolog.market.model.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kh.ecolog.auth.model.vo.CustomUserDetails;
import com.kh.ecolog.auth.util.SecurityUtil;
import com.kh.ecolog.market.model.dao.MarketCommentMapper;
import com.kh.ecolog.market.model.dto.MarketCommentDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarketCommentServiceImpl implements MarketCommentService  {
	
	private final MarketCommentMapper commentMapper;

	@Override
	public void insertComment(MarketCommentDTO dto) {
		Long userId = SecurityUtil.getCurrentUserId();
	    dto.setUserId(userId);
	    
		commentMapper.insertComment(dto);
		
	}

	@Override
	public List<MarketCommentDTO> selectCommentsByMarketNo(Long marketNo) {
	    List<MarketCommentDTO> comments = commentMapper.selectCommentsByMarketNo(marketNo);

	    Long currentUserId = null;
	    try {
	        currentUserId = SecurityUtil.getCurrentUserId();  // 로그인한 사용자 ID
	        System.out.println("현재 로그인한 사용자 ID: " + currentUserId);
	    } catch (Exception e) {
	        System.out.println("로그인 안 함");
	    }

	    if (currentUserId != null) {
	        for (MarketCommentDTO comment : comments) {
	            boolean isMine = comment.getUserId().equals(currentUserId);
	            comment.setIsMine(isMine);
	            System.out.println("댓글 번호: " + comment.getMarketCommentNo() + 
	                               ", 작성자 ID: " + comment.getUserId() + 
	                               ", 현재 사용자 ID: " + currentUserId + 
	                               ", isMine: " + isMine);
	        }
	    } else {
	        for (MarketCommentDTO comment : comments) {
	            comment.setIsMine(null); // 로그인 안 했을 경우 null
	            System.out.println("댓글 번호: " + comment.getMarketCommentNo() + 
	                               ", 로그인 안 함, isMine: null");
	        }
	    }

	    return comments;
	}
	
	@Override
	public void updateComment(MarketCommentDTO dto) {
		Long userId = SecurityUtil.getCurrentUserId();
	    dto.setUserId(userId);
	    
		commentMapper.updateComment(dto);
		
	}
	
	@Override
	public void deleteComment(Long commentNo) {
	    Long userId = SecurityUtil.getCurrentUserId();

	    // 댓글 작성자 확인
	    MarketCommentDTO comment = commentMapper.selectCommentByNo(commentNo);
	    if (!comment.getUserId().equals(userId)) {
	        throw new SecurityException("본인 댓글만 삭제할 수 있습니다.");
	    }

	    commentMapper.deleteComment(commentNo);
	}
}
