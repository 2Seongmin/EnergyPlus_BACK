package com.kh.ecolog.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserInfoDTO {

	private Long id; // 카카오에서 제공하는 사용자 아이디
	private String email;
	private String nickname;
	private String phoneNumber;
	
}
