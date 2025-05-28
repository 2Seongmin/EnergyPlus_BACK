package com.kh.ecolog.notice.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.kh.ecolog.exception.NoticeNotFoundException;
import com.kh.ecolog.notice.model.dao.NoticeMapper;
import com.kh.ecolog.notice.model.dto.NoticeDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;
    private static final int PAGE_SIZE = 10;

    /**
     * 공지사항 목록 조회 (페이징 및 검색 포함)
     */
    @Override
    public List<NoticeDTO> findAll(int pageNo, String keyword) {
        RowBounds rowBounds = new RowBounds(pageNo * PAGE_SIZE, PAGE_SIZE);

        if (keyword != null && !keyword.trim().isEmpty()) {
            Map<String, Object> param = new HashMap<>();
            param.put("keyword", keyword);
            return noticeMapper.searchNotice(param, rowBounds);
        }

        return noticeMapper.findAll(rowBounds);
    }

    /**
     * 공지사항 상세 조회
     */
    @Override
    public NoticeDTO findById(Long noticeId) {
        NoticeDTO notice = noticeMapper.findById(noticeId);
        if (notice == null) {
            throw new NoticeNotFoundException("해당 공지사항이 존재하지 않습니다.");
        }
        return notice;
    }

    /**
     * 전체 페이지 수 계산 (검색 포함)
     */
    @Override
    public int getTotalPages(String keyword) {
        int totalCount;

        if (keyword != null && !keyword.trim().isEmpty()) {
            Map<String, Object> param = new HashMap<>();
            param.put("keyword", keyword);
            totalCount = noticeMapper.countSearch(param);
        } else {
            totalCount = noticeMapper.countAll();
        }

        return (int) Math.ceil((double) totalCount / PAGE_SIZE);
    }
}
