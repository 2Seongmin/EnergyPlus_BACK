package com.kh.ecolog.member.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@Builder
public class Member {
	
	private Long userId;
	private Integer gradeId;
	private String userEmail;
	private String userPassword;
	private String userName;
	private String userPhone;
	private String role;

}
