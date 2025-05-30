package com.kh.ecolog.qnareply.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.ecolog.qna.model.service.QnaService;
import com.kh.ecolog.qnareply.model.dao.QnaReplyMapper;
import com.kh.ecolog.qnareply.model.dto.QnaReplyDTO;
import com.kh.ecolog.qnareply.model.vo.QnaReply;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class QnaReplyServiceImpl implements QnaReplyService {

	private final QnaReplyMapper qnaReplyMapper;
	private final QnaService qnaService;
	
	@Override
	@Transactional
	public void insertReply(QnaReplyDTO reply) {
		qnaService.selectById(reply.getQnaId());
		
		QnaReply requestData = QnaReply.builder()
									   .qnaReply(reply.getQnaReply())
									   .qnaId(reply.getQnaId())
									   .build();
		qnaReplyMapper.insertReply(requestData);
		
		// qna status 상태 업데이트
		qnaReplyMapper.updateQnaStatusToY(reply.getQnaId());
	}
	
	@Override
	@Transactional
	public void deleteById(Long replyId) {
		Long qnaId = qnaReplyMapper.findQnaIdByReplyId(replyId);
		qnaReplyMapper.deleteById(replyId);
		
		// 해당 QNA에 남은 댓글 수 확인
		int count = qnaReplyMapper.countByQnaId(qnaId);
		
		// 댓글 없으면 상태 N으로 변경
		if(count == 0) {
			qnaReplyMapper.updateQnaStatusToN(qnaId);
		}
	}

	@Override
	public List<QnaReplyDTO> selectReplyList(Long qnaId) {
		try {
	        qnaService.selectById(qnaId); // 존재 확인용
	    } catch (RuntimeException e) {
	        return new ArrayList(); // 게시글이 없으면 댓글도 없음
	    }
		return qnaReplyMapper.selectReplyList(qnaId);
	}
	
	@Override
	public QnaReplyDTO update(QnaReplyDTO reply) {
		qnaReplyMapper.update(reply);
		return reply;
	}

}
