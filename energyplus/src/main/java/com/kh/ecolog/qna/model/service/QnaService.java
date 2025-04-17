package com.kh.ecolog.qna.model.service;

import java.util.List;

import com.kh.ecolog.qna.model.dto.QnaDTO;

public interface QnaService {
	
	void insert(QnaDTO qna);
	
	List<QnaDTO> selectAll(int pageNo);
	
	QnaDTO selectById(Long qnaId);
	
	QnaDTO update(QnaDTO qna);
	
	void deleteById(Long qnaId);

}
