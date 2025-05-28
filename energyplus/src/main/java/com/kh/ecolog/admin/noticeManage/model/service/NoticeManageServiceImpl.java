package com.kh.ecolog.admin.noticeManage.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import com.kh.ecolog.admin.noticeManage.model.dao.NoticeManageMapper;
import com.kh.ecolog.admin.noticeManage.model.dto.NoticeManageDTO;
import com.kh.ecolog.admin.noticeManage.model.vo.NoticeManage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeManageServiceImpl implements NoticeManageService {

    private final NoticeManageMapper noticeManageMapper;
    private static final int PAGE_SIZE = 10;

    /**
     * 공지 등록
     */
    @Override
    public void save(NoticeManageDTO notice) {

        NoticeManage requestData = NoticeManage.builder()
            .noticeTitle(notice.getNoticeTitle())
            .noticeContent(notice.getNoticeContent())
            .userId(1L)
            .build();

        noticeManageMapper.save(requestData);
    }

    /**
     * 공지 목록 조회 (페이징 및 검색)
     */
    @Override
    public List<NoticeManageDTO> findAll(int pageNo, String keyword) {
        RowBounds rowBounds = new RowBounds(pageNo * PAGE_SIZE, PAGE_SIZE);

        if (keyword != null && !keyword.trim().isEmpty()) {
            Map<String, Object> param = new HashMap<>();
            param.put("keyword", keyword);
            return noticeManageMapper.searchNotice(param, rowBounds);
        }

        return noticeManageMapper.findAll(rowBounds);
    }

    /**
     * 공지 상세 조회
     */
    @Override
    public NoticeManageDTO findById(Long noticeId) {
        NoticeManageDTO notice = noticeManageMapper.findById(noticeId);
        if (notice == null) {
            throw new RuntimeException("해당 공지사항이 존재하지 않습니다.");
        }
        return notice;
    }

    /**
     * 공지 수정
     */
    @Override
    public NoticeManageDTO update(NoticeManageDTO notice) {
        noticeManageMapper.update(notice);
        
        return notice;
    }

    /**
     * 공지 삭제
     */
    @Override
    public void deleteById(Long noticeId) {
        noticeManageMapper.deleteById(noticeId);
    }

    /**
     * 전체 페이지 수 계산
     */
    @Override
    public int getTotalPages(String keyword) {
        int totalCount;

        if (keyword != null && !keyword.trim().isEmpty()) {
            Map<String, Object> param = new HashMap<>();
            param.put("keyword", keyword);
            totalCount = noticeManageMapper.countSearch(param);
        } else {
            totalCount = noticeManageMapper.countAll();
        }

        return (int) Math.ceil((double) totalCount / PAGE_SIZE);
    }
}
