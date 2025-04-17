package com.kh.ecolog.member.model.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.kh.ecolog.member.model.dto.MemberDTO;
import com.kh.ecolog.member.model.vo.Member;

@Mapper
public interface MemberMapper {
	
	@Insert("INSERT INTO TB_USER (USER_ID, GRADE_ID, USER_EMAIL, USER_PASSWORD, USER_NAME, USER_PHONE, ROLE) " +
	        "VALUES (SEQ_USER.NEXTVAL, #{gradeId}, #{userEmail}, #{userPassword}, #{userName}, #{userPhone}, #{role})")
	int signUp(Member member);
	
	@Select("SELECT USER_ID userId, GRADE_ID gradeId, USER_EMAIL userEmail, USER_PASSWORD userPassword, USER_NAME userName, USER_PHONE userPhone, ROLE role" +
			" FROM TB_USER WHERE USER_EMAIL=#{userEmail}")
	MemberDTO getMemberByMemberEmail(String memberEmail);
}