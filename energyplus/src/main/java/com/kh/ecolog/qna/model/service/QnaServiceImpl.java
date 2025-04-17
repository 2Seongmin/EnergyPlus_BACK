package com.kh.ecolog.qna.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

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

	@Override
	public void insert(QnaDTO qna) {
		Qna requestData = null;
		
		requestData = Qna.builder()
						 .qnaTitle(qna.getQnaTitle())
						 .qnaContent(qna.getQnaContent())
						 .userId(qna.getUserId())
						 .build();
		qnaMapper.insert(requestData);
	}

	@Override
	public List<QnaDTO> selectAll(int pageNo) {
		return null;
	}

	@Override
	public QnaDTO selectById(Long qnaId) {
		return null;
	}

	@Override
	public QnaDTO update(QnaDTO qna) {
		qnaMapper.update(qna);
		return qna;
	}

	@Override
	public void deleteById(Long qnaId) {

	}

}
