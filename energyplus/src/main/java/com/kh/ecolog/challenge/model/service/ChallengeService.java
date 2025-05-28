package com.kh.ecolog.challenge.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.ecolog.challenge.model.dto.ChallengeDTO;

public interface ChallengeService {

	
	List<ChallengeDTO> findAllChallenge(int pageNo,String keyword); // 챌린지 목록 조회 

    ChallengeDTO getChallengeDetail(Long challengeSeq); // 챌린지 상세 보기 

    void saveChallenge(ChallengeDTO challenge, MultipartFile file); // 챌린지 저장 
    
    void deleteChallenge(Long challengeSeq); // 챌린지 삭제 
    
    ChallengeDTO updateChallenge(ChallengeDTO challenge, MultipartFile file); // 챌린지 수정 
    
    int getTotalPages(String keyword);
    
    boolean isOwner(Long challengeSeq, Long userId); // 글 작성자인지 확인

}
