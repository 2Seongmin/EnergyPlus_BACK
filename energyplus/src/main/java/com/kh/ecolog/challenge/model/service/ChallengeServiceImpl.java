package com.kh.ecolog.challenge.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.ecolog.auth.model.vo.CustomUserDetails;
import com.kh.ecolog.auth.service.AuthService;
import com.kh.ecolog.challenge.model.dao.ChallengeMapper;
import com.kh.ecolog.challenge.model.dto.ChallengeDTO;
import com.kh.ecolog.challenge.model.vo.Challenge;
import com.kh.ecolog.file.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeMapper challengeMapper;
    private final FileService fileService;
    private final AuthService authService;

    // 챌린지 저장 
    @Override
    public void saveChallenge(ChallengeDTO challenge, MultipartFile file) {
    	
    	CustomUserDetails user = authService.getUserDetails();
        Long userId = user.getUserId();
        Challenge requestData;
      
        if (file != null && !file.isEmpty()) {
            
            String fileUrl = fileService.store(file);
            requestData = Challenge.builder()
                    .challengeTitle(challenge.getChallengeTitle())
                    .challengeContent(challenge.getChallengeContent())
                    .userId(userId)
                    .challengeImg(fileUrl)
                    .challengeStatus("N")
                    .build();
        } else {
            requestData = Challenge.builder()
                    .challengeTitle(challenge.getChallengeTitle())
                    .challengeContent(challenge.getChallengeContent())
                    .userId(userId)
                    .challengeStatus("N")
                    .build();
        }
        challengeMapper.saveChallenge(requestData);
    }
    	
    // 챌린지 목록 조회 
    @Override
    public List<ChallengeDTO> findAllChallenge(int pageNo, String keyword) {
    	int size = 10;
    	RowBounds rowBounds = new RowBounds(pageNo * size, size);
    	
    	if (keyword != null && !keyword.trim().isEmpty()) {
            Map<String, Object> param = new HashMap<>();
            param.put("keyword", keyword);
            return challengeMapper.searchChallenge(param, rowBounds);
        }
    	
        return challengeMapper.findAllChallenge(rowBounds);
    }
    
    // 챌린지 상세 보기 
    @Override
    public ChallengeDTO getChallengeDetail(Long challengeSeq) {
        return challengeMapper.selectChallengeDetail(challengeSeq);
    }
    
    // 챌린지 삭제 
    @Override
    public void deleteChallenge(Long challengeSeq) {
    	challengeMapper.deleteChallenge(challengeSeq);
    }
    
    // 챌린지 수정
    @Override
    public ChallengeDTO updateChallenge(ChallengeDTO challenge, MultipartFile file) {
    	
    	if(file != null && !file.isEmpty()) {
    		
    		String filePath = fileService.store(file);
    		challenge.setChallengeImg(filePath);
    	}
    	challengeMapper.updateChallenge(challenge);
    	return challenge;
    }
    
    // 페이지 수 계산 
    @Override
    public int getTotalPages(String keyword) {
        int size = 10;
        int totalCount;

        if (keyword != null && !keyword.trim().isEmpty()) {
            Map<String, Object> param = new HashMap<>();
            param.put("keyword", keyword);
            totalCount = challengeMapper.countSearch(param);
        } else {
            totalCount = challengeMapper.countAll();
        }

        return (int) Math.ceil((double) totalCount / size);
    }

    // 작성자 확인 
    @Override
    public boolean isOwner(Long challengeSeq, Long userId) {
        Long writerId = challengeMapper.findWriterIdByChallengeSeq(challengeSeq);
        return writerId != null && writerId.equals(userId);
    }

    
    
}
