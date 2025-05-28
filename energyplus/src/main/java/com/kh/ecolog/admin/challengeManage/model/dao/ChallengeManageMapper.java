package com.kh.ecolog.admin.challengeManage.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kh.ecolog.admin.challengeManage.model.dto.ChallengeManageDTO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface ChallengeManageMapper {

	// 1. 전체 조회 
	List<ChallengeManageDTO> selectAllChallenges(RowBounds rowBounds);

    // 2. 참여 상세 조회
    ChallengeManageDTO selectChallengeDetail(Long challengeSeq);

    // 3. 참여 상태 업데이트 (승인)
    void approveChallenge(Long challengeSeq);

    // 4. 마일리지 지급 내역 삽입
    void updateMileageRewarded(@Param("challengeSeq") Long challengeSeq,
            @Param("mileage") long mileage);

    // 5. 반려 처리
    void rejectChallenge(@Param("challengeSeq") Long challengeSeq,
                             @Param("reason") String rejectReason);
    
    // 6. 반려 사유 저장
    void updateRejectReason(@Param("challengeSeq") Long challengeSeq,
    						@Param("reason") String reason);


    void insertParticipationIfMissing(Long challengeSeq);
    

    int countAll();
    
    int countSearch(Map<String, Object> param);
    
    List<ChallengeManageDTO> searchChallenges(Map<String, Object> param, RowBounds rowbounds);



}
