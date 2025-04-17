package com.kh.ecolog.qna.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.ecolog.qna.model.dto.QnaDTO;
import com.kh.ecolog.qna.model.vo.Qna;

@Mapper
public interface QnaMapper {
	
	void insert(Qna qna);
	
	List<QnaDTO> selectAll(RowBounds rb);
	
	QnaDTO selectById(Long qnaId);
	
	int update(QnaDTO qna);
	
	void deleteById(Long qnaId);
	
	// --------아래부터 댓글--------
}
