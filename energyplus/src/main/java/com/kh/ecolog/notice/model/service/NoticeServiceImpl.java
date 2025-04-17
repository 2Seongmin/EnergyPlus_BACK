package com.kh.ecolog.notice.model.service;

import java.util.List;

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
    public void save(NoticeDTO notice) {
        // 로그인 기능 미적용 상태이므로 userId 임의 지정
        Long userId = 1L;

        Notice requestData = Notice.builder()
                .noticeTitle(notice.getNoticeTitle())
                .noticeContent(notice.getNoticeContent())
                .userId(userId)
                .build();

        noticeMapper.save(requestData);
    }

    @Override
    public List<NoticeDTO> findAll(int pageNo) {
        int size = 5;  // 페이지 당 5
        RowBounds rowBounds = new RowBounds(pageNo * size, size);
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

    @Override
    public NoticeDTO update(NoticeDTO notice) {
        noticeMapper.update(notice);
        return notice;
    }

    @Override
    public void deleteById(Long noticeId) {
        noticeMapper.deleteById(noticeId);
    }
	

}
