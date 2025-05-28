package com.kh.ecolog.challenge.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.kh.ecolog.challenge.model.dto.ChallengeDTO;
import com.kh.ecolog.challenge.model.vo.Challenge;

@Mapper
public interface ChallengeMapper {

	List<ChallengeDTO> findAllChallenge (RowBounds rowBounds); // 전체 조회 

    ChallengeDTO selectChallengeDetail(Long challengeSeq); // 특정 챌린지 상세보기 
   
    void saveChallenge(Challenge challenge); // 챌린지 등록 
    
    void deleteChallenge(@Param("challengeSeq") long challengeSeq); // 챌린지 삭제 
    
    void updateChallenge(ChallengeDTO challenge); // 챌린지 수정 
    
    List<ChallengeDTO> searchChallenge(Map<String, Object> param, RowBounds rowbounds);
    
    int countAll();
    
    int countSearch(Map<String, Object> param);
    
    Long findWriterIdByChallengeSeq(Long challengeSeq); // 작성자인지 확인 (userId 비교) 
    
    
}
