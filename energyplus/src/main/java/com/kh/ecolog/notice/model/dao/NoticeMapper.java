package com.kh.ecolog.notice.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.kh.ecolog.notice.model.dto.NoticeDTO;
import com.kh.ecolog.notice.model.vo.Notice;

@Mapper
public interface NoticeMapper {

	List<NoticeDTO> findAll (RowBounds rowBounds); // 전채 조회 

	NoticeDTO findById(Long noticeId);
	
    // 검색
    List<NoticeDTO> searchNotice(Map<String, Object> param, RowBounds rowBounds);
    
    int countAll();

    int countSearch(Map<String, Object> param);


	
}























