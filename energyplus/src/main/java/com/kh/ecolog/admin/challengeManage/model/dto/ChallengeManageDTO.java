package com.kh.ecolog.admin.challengeManage.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChallengeManageDTO {

	 private Long participationSeq;  
	 private Long challengeSeq; 
	 private Long mileageRewarded;
	 private Date rewardedDate;
	 private String rejectReason;
	 
	 
	 private String challengeTitle;    // 챌린지 제목
	 private String challengeContent;  // 챌린지 내용
	 private Long challengeWriter;     // 작성자 ID
	 private String challengeImg;
	 private String userName; // 작성자 이름
	 private String challengeStatus;

	
}
