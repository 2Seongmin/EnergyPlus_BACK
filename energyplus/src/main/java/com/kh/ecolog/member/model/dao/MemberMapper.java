package com.kh.ecolog.member.model.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.ecolog.member.model.dto.MarketingAgreementDTO;
import com.kh.ecolog.member.model.dto.MemberDTO;
import com.kh.ecolog.member.model.vo.Member;

@Mapper
public interface MemberMapper {
    
    @Insert("INSERT INTO TB_USER (USER_ID, GRADE_ID, USER_EMAIL, USER_PASSWORD, USER_NAME, USER_PHONE, ROLE, STATUS) " +
            "VALUES (SEQ_USER.NEXTVAL, #{gradeId}, #{userEmail}, #{userPassword}, #{userName}, #{userPhone}, #{role}, 'Y')")
    int signUp(Member member);
    
    @Select("SELECT USER_ID userId, GRADE_ID gradeId, USER_EMAIL userEmail, USER_PASSWORD userPassword, USER_NAME userName, USER_PHONE userPhone, ROLE role, STATUS status" +
            " FROM TB_USER WHERE USER_EMAIL=#{userEmail}")
    MemberDTO getMemberByMemberEmail(String memberEmail);
    
    // 이메일로 사용자 존재 여부 확인
    @Select("SELECT COUNT(*) FROM TB_USER WHERE USER_EMAIL=#{userEmail}")
    int existsByEmail(String userEmail);
    
    // 회원 ID로 회원 정보 조회
    @Select("SELECT USER_ID userId, GRADE_ID gradeId, USER_EMAIL userEmail, USER_PASSWORD userPassword, USER_NAME userName, USER_PHONE userPhone, ROLE role, STATUS status" +
            " FROM TB_USER WHERE USER_ID=#{userId}")
    MemberDTO getMemberByUserId(Long userId);
    
    // 회원 탈퇴 (상태 변경)
    @Update("UPDATE TB_USER SET STATUS='N' WHERE USER_ID=#{userId}")
    int withdrawMember(Long userId);
    

    // OAuth2.0 회원정보 수정
    @Update("UPDATE TB_USER SET USER_NAME = #{userName} WHERE USER_ID = #{userId}")
    void updateMemberName(Member member);
    

    // 비밀번호 업데이트
    @Update("UPDATE TB_USER SET USER_PASSWORD=#{password} WHERE USER_EMAIL=#{email}")
    int updatePassword(@Param("email") String email, @Param("password") String password);
    
    // 마케팅 동의 .. => 밑에 회원 테이블에 업데이트 시켰기에 굳이 안넣어도 될듯?
//    @Insert("INSERT INTO TB_MARKETING_AGREEMENT (AGREEMENT_ID, USER_ID, MARKETING_AGREED) VALUES (SEQ_MARKETING_AGREEMENT.NEXTVAL, #{userId}, #{marketingAgreed})")
//    int saveMarketingAgreement(MarketingAgreementDTO agreementDTO);
//    
//    @Select("SELECT AGREEMENT_ID agreementId, USER_ID userId, MARKETING_AGREED marketingAgreed, TO_CHAR(AGREEMENT_DATE, 'YYYY-MM-DD') agreementDate FROM TB_MARKETING_AGREEMENT WHERE USER_ID = #{userId}")
//    MarketingAgreementDTO getMarketingAgreementByUserId(Long userId);
    
    // 마케팅 동의 정보 회운에 업데이트
    @Update("UPDATE TB_USER SET MARKETING_AGREED=#{marketingAgreed} WHERE USER_ID=#{userId}")
    int updateMarketingAgreed(Long userId, String marketingAgreed);
    

}