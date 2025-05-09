package com.kh.ecolog.notice.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.kh.ecolog.notice.model.dao.NoticeMapper;
import com.kh.ecolog.notice.model.dto.NoticeDTO;
import com.kh.ecolog.notice.model.vo.Notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeServiceImpl implements NoticeService{
	
	private final NoticeMapper noticeMapper;
	
    @Override
    public List<NoticeDTO> findAll(int pageNo, String keyword) {
        int size = 10;
        RowBounds rowBounds = new RowBounds(pageNo * size, size);
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            Map<String, Object> param = new HashMap<>();
            param.put("keyword", keyword);
            return noticeMapper.searchNotice(param, rowBounds);
        }
        
        return noticeMapper.findAll(rowBounds);
    }

    @Override
    public NoticeDTO findById(Long noticeId) {
        NoticeDTO notice = noticeMapper.findById(noticeId);
        if (notice == null) {
            throw new RuntimeException("해당 공지사항이 존재하지 않습니다.");
        }
        return notice;
    }

    // 페이지 수 계산 
    @Override
    public int getTotalPages(String keyword) {
        int size = 10;
        int totalCount;

        if (keyword != null && !keyword.trim().isEmpty()) {
            Map<String, Object> param = new HashMap<>();
            param.put("keyword", keyword);
            totalCount = noticeMapper.countSearch(param);
        } else {
            totalCount = noticeMapper.countAll();
        }

        return (int) Math.ceil((double) totalCount / size);
    }


}
