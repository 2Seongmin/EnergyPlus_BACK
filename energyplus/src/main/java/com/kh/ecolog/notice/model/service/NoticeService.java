package com.kh.ecolog.notice.model.service;

import java.util.List;

import com.kh.ecolog.notice.model.dto.NoticeDTO;

public interface NoticeService {
	
    List<NoticeDTO> findAll(int pageNo, String keyword);
    
    NoticeDTO findById(Long noticeId);
    
    int getTotalPages(String keyword);

}
