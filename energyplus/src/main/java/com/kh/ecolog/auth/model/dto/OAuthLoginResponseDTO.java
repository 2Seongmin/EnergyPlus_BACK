package com.kh.ecolog.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuthLoginResponseDTO {

	private String accessToken;
	private String refreshToken;
	private String userEmail;
	private String userName;
	private String userRole;
	private String userPhone;
	private boolean isNewUser; // 신규가입 여부
	
}
