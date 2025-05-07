package com.kh.ecolog.auth.service;

import com.kh.ecolog.auth.model.dto.KakaoUserInfoDTO;
import com.kh.ecolog.auth.model.dto.OAuthLoginResponseDTO;

public interface OAuthService {

	// 카카오인증 코드 -> 액세스토큰 얻음
	String getKaKaoAccessToken(String code);
	
	// 얻은 액세스 토큰으로 사용자 정보 조회
	KakaoUserInfoDTO getKakaoUserInfo(String accessToken);
	
    // 카카오 로그인 처리
    OAuthLoginResponseDTO processKakaoLogin(KakaoUserInfoDTO kakaoUserInfo);
	
}
