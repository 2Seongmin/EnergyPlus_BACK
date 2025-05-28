package com.kh.ecolog.admin.challengeManage.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.kh.ecolog.admin.challengeManage.model.dto.ChallengeManageDTO;
import com.kh.ecolog.admin.challengeManage.model.service.ChallengeManageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/admin/challenges")
public class ChallengeManageController {

	private final ChallengeManageService challengeManageService;

	// 전체 목록 조회 
	@GetMapping
	public ResponseEntity<List<ChallengeManageDTO>> findAll(@RequestParam(name = "page", defaultValue = "0") int page,
															@RequestParam(name = "keyword", required = false) String keyword) {

		return ResponseEntity.ok(challengeManageService.findAllChallenges(page, keyword));
	}
	
	// 페이지 수 조회 
    @GetMapping("/pages")
    public ResponseEntity<Integer> getPageCount(@RequestParam(name = "keyword",required = false) String keyword){
    	log.info("📄 페이지 수 요청: keyword={}", keyword);

    	return ResponseEntity.ok(challengeManageService.getTotalPages(keyword));
    }

	// 2. 특정 참여 상세 조회
	@GetMapping("/{challengeSeq}")
	public ResponseEntity<ChallengeManageDTO> getChallengeDetail(
			@PathVariable("challengeSeq") Long challengeSeq) {

		ChallengeManageDTO challenge = challengeManageService.getChallengeDetail(challengeSeq);

		return ResponseEntity.ok(challenge);
	}

	// 3. 참여 승인 (마일리지 지급)
	@PutMapping("/{challengeSeq}/approve")
	public ResponseEntity<?> approveChallenge(@PathVariable("challengeSeq") Long challengeSeq,
											  @RequestBody Map<String, Object> body) {

	    if (!body.containsKey("mileage")) {
	        throw new IllegalArgumentException("마일리지 값이 없습니다.");
	    }

	    long mileage = Long.parseLong(body.get("mileage").toString());
	    challengeManageService.approveChallenge(challengeSeq, mileage);
	    return ResponseEntity.ok("마일리지 지급 및 승인 완료");
	}


	// 4. 참여 반려
	@PutMapping("/{challengeSeq}/reject")
	public ResponseEntity<?> rejectChallenge(@PathVariable("challengeSeq") Long challengeSeq,
	                                         @RequestBody Map<String, String> body) {

		String reason = body.get("rejectReason");
		challengeManageService.rejectChallenge(challengeSeq, reason);
		return ResponseEntity.ok("챌린지가 반려되었습니다.");
	}
	

}
