package com.kh.ecolog.notice.model.service;

import java.util.List;

import com.kh.ecolog.notice.model.dto.NoticeDTO;

public interface NoticeService {
	
	void save(NoticeDTO notice);
	
    List<NoticeDTO> findAll(int pageNo);
    
    NoticeDTO findById(Long noticeId);
    
    NoticeDTO update(NoticeDTO notice);
    
    void deleteById(Long noticeId);
}
