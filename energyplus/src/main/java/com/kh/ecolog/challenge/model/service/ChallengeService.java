package com.kh.ecolog.challenge.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.ecolog.challenge.model.dto.ChallengeDTO;

public interface ChallengeService {

	
	List<ChallengeDTO> getChallengeList(); // 챌린지 목록 조회 

    ChallengeDTO getChallengeDetail(Long challengeSeq); // 챌린지 상세 보기 

    void saveChallenge(ChallengeDTO challenge, MultipartFile file); // 챌린지 저장 
}
