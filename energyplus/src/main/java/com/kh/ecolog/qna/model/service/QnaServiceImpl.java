package com.kh.ecolog.qna.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.kh.ecolog.auth.model.vo.CustomUserDetails;
import com.kh.ecolog.auth.service.AuthService;
import com.kh.ecolog.qna.model.dao.QnaMapper;
import com.kh.ecolog.qna.model.dto.QnaDTO;
import com.kh.ecolog.qna.model.vo.Qna;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class QnaServiceImpl implements QnaService {
	
	private final QnaMapper	qnaMapper;
	private final AuthService authService;

	@Override
	public void insert(QnaDTO qna) {
		Long userId = getCurrentUserId();
		Qna requestData = Qna.builder()
						 .qnaTitle(qna.getQnaTitle())
						 .qnaContent(qna.getQnaContent())
						 .userId(userId)
						 .build();
		qnaMapper.insertQna(requestData);
	}

	// 전체 조회 및 페이징 처리
	@Override
	public Map<String, Object> selectAll(int pageNo, String keyword) {
		Long userId = getCurrentUserId();
		
		int size = 5;
		RowBounds rowBounds = new RowBounds(pageNo * size, size);
		
		List<QnaDTO> list;
	    int totalCount;

	    if (keyword == null || keyword.trim().isEmpty()) {
	        list = qnaMapper.selectAll(userId, rowBounds);
	        totalCount = qnaMapper.countAll(userId);
	    } else {
	    	Map<String, Object> param = new HashMap();
	    	param.put("userId", userId);
	    	param.put("keyword", keyword);
	    	
	        list = qnaMapper.searchQna(param, rowBounds);
	        totalCount = qnaMapper.countSearch(param);
	    }
		
		Map<String, Object> result = new HashMap();
		result.put("list", list);
		result.put("totalCount", totalCount);
		return result;
	}

	@Override
	public QnaDTO selectById(Long qnaId) {
		QnaDTO qna = qnaMapper.selectById(qnaId);
		if(qna == null) {
			throw new RuntimeException("QnA 게시글이 존재하지 않습니다.");
		}
		return qna;
	}

	@Override
	public QnaDTO update(QnaDTO qna) {
		qnaMapper.updateQna(qna);
		return qna;
	}

	@Override
	public void deleteById(Long qnaId) {
		qnaMapper.deleteById(qnaId);
	}
	
	// userId
	private Long getCurrentUserId() {
		return authService.getUserDetails().getUserId();
	}

}
