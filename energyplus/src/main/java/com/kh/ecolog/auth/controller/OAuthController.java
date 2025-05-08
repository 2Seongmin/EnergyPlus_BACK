package com.kh.ecolog.auth.controller;

import com.kh.ecolog.auth.model.dto.KakaoUserInfoDTO;
import com.kh.ecolog.auth.model.dto.OAuthLoginResponseDTO;
import com.kh.ecolog.auth.service.OAuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;
    
    @Value("${oauth2.kakao.base-url}")
    private String baseUrl;
    
    @Value("${oauth2.kakao.client-id}")
    private String clientId;
    
    @Value("${oauth2.kakao.redirect-uri}")
    private String redirectUri;

    /**
     * 카카오 로그인 URL 생성
     */
    @GetMapping("/kakao/url")
    public ResponseEntity<?> getKakaoLoginUrl() {
        try {
            String kakaoLoginUrl = baseUrl + 
                                  "?client_id=" + clientId + 
                                  "&redirect_uri=" + redirectUri + 
                                  "&response_type=code" + 
                                  "&scope=account_email,profile_nickname,phone_number";
            
            return ResponseEntity.ok().body(Map.of("loginUrl", kakaoLoginUrl));
        } catch (Exception e) {
            log.error("카카오 로그인 URL 생성 오류", e);
            return ResponseEntity.badRequest().body(Map.of("error", "카카오 로그인 URL 생성 중 오류가 발생"));
        }
    }

    /**
     * 카카오 로그인 콜백 처리
     */
    @GetMapping("/kakao/callback")
    public void kakaoCallback(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        log.info("카카오 콜백 - 인증 코드: {}", code);
        
        try {
        	
            String accessToken = oAuthService.getKaKaoAccessToken(code);
            
            KakaoUserInfoDTO kakaoUserInfo = oAuthService.getKakaoUserInfo(accessToken);
            
            OAuthLoginResponseDTO loginResponse = oAuthService.processKakaoLogin(kakaoUserInfo);
            
            // 프론트엔드로 리다이렉트 (토큰 정보를 쿼리 파라미터로 전달)
            String redirectUrl = "http://localhost:5173/login-success" + 
                               "?token=" + URLEncoder.encode(loginResponse.getAccessToken(), "UTF-8") + 
                               "&refreshToken=" + URLEncoder.encode(loginResponse.getRefreshToken(), "UTF-8") + 
                               "&userEmail=" + URLEncoder.encode(loginResponse.getUserEmail(), "UTF-8") + 
                               "&userName=" + URLEncoder.encode(loginResponse.getUserName(), "UTF-8") + 
                               "&userRole=" + URLEncoder.encode(loginResponse.getUserRole(), "UTF-8") + 
                               "&isNewUser=" + loginResponse.isNewUser();
            
            response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            log.error("카카오 로그인 처리 중 오류 발생", e);
            response.sendRedirect("http://localhost:5173/login?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
 }

