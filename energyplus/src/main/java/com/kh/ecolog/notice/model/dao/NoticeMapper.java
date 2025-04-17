package com.kh.ecolog.notice.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import com.kh.ecolog.notice.model.dto.NoticeDTO;
import com.kh.ecolog.notice.model.vo.Notice;

@Mapper
public interface NoticeMapper {

	void save(Notice notice);
	
	List<NoticeDTO> findAll (RowBounds rowBounds);
	
	NoticeDTO findById(Long noticeId);
	
	void update(NoticeDTO notice);
	
    void deleteById(@Param("noticeId") Long noticeId);


	
}























