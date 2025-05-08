package com.kh.ecolog.auth.service;


import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.ecolog.auth.model.dao.OAuthMapper;
import com.kh.ecolog.auth.model.dto.KakaoUserInfoDTO;
import com.kh.ecolog.auth.model.dto.OAuthLoginResponseDTO;
import com.kh.ecolog.auth.model.dto.OAuthUserDTO;
import com.kh.ecolog.exception.OAuthProcessingException;
import org.springframework.transaction.annotation.Transactional;
import com.kh.ecolog.member.model.dao.MemberMapper;
import com.kh.ecolog.member.model.dto.MemberDTO;
import com.kh.ecolog.member.model.vo.Member;
import com.kh.ecolog.token.model.service.TokenService;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {
	
	private final ObjectMapper objectMapper;
	private final RestTemplate restTemplate;
	private final OAuthMapper oAuthMapper;
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
 
	
	
	
    @Value("${oauth2.kakao.client-id}")
    private String clientId;
    
    @Value("${oauth2.kakao.redirect-uri}")
    private String redirectUri;
    
    @Value("${oauth2.kakao.client-secret}")
    private String clientSecret;

    @Override
    public String getKaKaoAccessToken(String code) {
        log.info("카카오 액세스 토큰 요청 >> 인증 코드: {}", code);
        log.info("사용 중인 client_id: {}", clientId); 
        log.info("사용 중인 redirect_uri: {}", redirectUri);
        
        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        // 요청 바디 설정
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);
        
        // HTTP 요청 엔티티 생성
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        
        try {
            // 요청 정보 로깅
            log.info("카카오 토큰 요청 바디: {}", body);
            
            // 카카오 토큰 API 호출
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://kauth.kakao.com/oauth/token",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            
            // 응답 처리
            if (response.getStatusCode() == HttpStatus.OK) {
                try {
                    JsonNode jsonNode = objectMapper.readTree(response.getBody());
                    return jsonNode.get("access_token").asText();
                } catch (JsonProcessingException e) {
                    log.error("카카오 토큰 파싱 오류", e);
                    throw new OAuthProcessingException("카카오 토큰 파싱 오류: " + e.getMessage());
                }
            } else {
                log.error("카카오 토큰 요청 실패: {}", response.getStatusCodeValue());
                throw new OAuthProcessingException("카카오 토큰 요청 실패");
            }
        } catch (Exception e) {
            log.error("카카오 토큰 요청 중 오류 발생", e);
            throw new OAuthProcessingException("카카오 토큰 요청 중 오류 발생: " + e.getMessage());
        }
    }
    
    
    @Override
    public KakaoUserInfoDTO getKakaoUserInfo(String accessToken) {
        log.info("카카오 사용자의 정보 요청 >>> 액세스 토큰: {}", accessToken);
        
        // 요청헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        // HTTP요청 Entity 생성
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(headers);
        
        try {
            // 사용자 정보 API 호출
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.GET,
                    requestEntity,
                    String.class
            );
            
            // 응답 처리
            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode jsonNode = objectMapper.readTree(response.getBody());
                
                Long id = jsonNode.get("id").asLong();
                String email = null;
                String nickname = null;
                String phoneNumber = null;
                
                // 계정정보 추출
                JsonNode kakaoAccountNode = jsonNode.get("kakao_account");
                if (kakaoAccountNode != null) {
                    // 이메일 추출
                    if (kakaoAccountNode.has("email")) {
                        email = kakaoAccountNode.get("email").asText();
                    }
                    
                    // 프로필정보(닉네임) 추출
                    JsonNode profileNode = kakaoAccountNode.get("profile");
                    if (profileNode != null && profileNode.has("nickname")) {
                        nickname = profileNode.get("nickname").asText();
                    }
                    
                    // 옵션에 전화번호정보 요청 추가
                    if (kakaoAccountNode.has("phone_number")) {
                        phoneNumber = kakaoAccountNode.get("phone_number").asText();
                        // 카카오는 +82 10-1234-5678 형식으로 제공!! 
                        phoneNumber = phoneNumber.replace("+82 ", "0"); // +82 10-1234-5678 -> 010-1234-5678로!!!
                        phoneNumber = phoneNumber.replaceAll("-", ""); // 010-1234-5678 -> 01012345678
                    }
                    
                }
                
                return KakaoUserInfoDTO.builder()
                        .id(id)
                        .email(email)
                        .nickname(nickname)
                        .phoneNumber(phoneNumber)
                        .build();
                
            } else {
                log.error("카카오 사용자 정보 요청 실패: {}", response.getStatusCodeValue());
                throw new OAuthProcessingException("카카오 사용자 정보 요청 실패");
            }
        } catch (JsonProcessingException e) {
            log.error("카카오 사용자 정보 파싱 오류", e);
            throw new OAuthProcessingException("카카오 사용자 정보 파싱 오류: " + e.getMessage());
        } catch (Exception e) {
            log.error("카카오 사용자 정보 요청 중 오류 발생", e);
            throw new OAuthProcessingException("카카오 사용자 정보 요청 중 오류 발생: " + e.getMessage());
        }
    }
	
    
    
    @Override
    @Transactional
    public OAuthLoginResponseDTO processKakaoLogin(KakaoUserInfoDTO kakaoUserInfo) {
        log.info("카카오 로그인 처리 - 사용자 정보: {}", kakaoUserInfo);
        
        // 이메일이 없는 경우
        if (kakaoUserInfo.getEmail() == null || kakaoUserInfo.getEmail().isEmpty()) {
            throw new OAuthProcessingException("카카오 계정에 이메일이 없습니다. 이메일이 필요합니다.");
        }
        
        // 카카오ID로 OAuth 사용자 조회
        OAuthUserDTO existingOAuthUser = oAuthMapper.findByProviderAndProviderId("kakao", String.valueOf(kakaoUserInfo.getId()));
        
        // 신규 사용자냐??
        boolean isNewUser = false;
        MemberDTO memberDTO;
        
        if (existingOAuthUser == null) {
            isNewUser = true;
            
            // 이메일로 기존 사용자 확인
            memberDTO = memberMapper.getMemberByMemberEmail(kakaoUserInfo.getEmail());
            
            if (memberDTO == null) {
                // 완전히 새로운 사용자 -> 등록!!
                String randomPassword = UUID.randomUUID().toString();
                
                
                memberDTO = new MemberDTO();
                memberDTO.setGradeId(1); // 기본등급(새싹)
                memberDTO.setUserEmail(kakaoUserInfo.getEmail());
                memberDTO.setUserPassword(randomPassword); // 임시 비번
                memberDTO.setUserName(kakaoUserInfo.getNickname());
                memberDTO.setUserPhone(kakaoUserInfo.getPhoneNumber());
                memberDTO.setRole("ROLE_USER");
                memberDTO.setStatus("Y");
                
                
                Member member = Member.builder()
                        .gradeId(memberDTO.getGradeId())
                        .userEmail(memberDTO.getUserEmail())
                        .userPassword(passwordEncoder.encode(memberDTO.getUserPassword()))
                        .userName(memberDTO.getUserName())
                        .userPhone(kakaoUserInfo.getPhoneNumber())
                        .role(memberDTO.getRole())
                        .build();
                
               
                memberMapper.signUp(member);
                
                
                memberDTO = memberMapper.getMemberByMemberEmail(kakaoUserInfo.getEmail());
            }
            
            // OAuth 사용자 정보 등록
            OAuthUserDTO oAuthUser = OAuthUserDTO.builder()
                    .userId(memberDTO.getUserId())
                    .provider("kakao")
                    .providerId(String.valueOf(kakaoUserInfo.getId()))
                    .email(kakaoUserInfo.getEmail())
                    .nickname(kakaoUserInfo.getNickname())
                    .build();
            
            oAuthMapper.saveOAuthUser(oAuthUser);
            
        } else {
            // 기존 OAuth 사용자인 경우라면 회원 정보 조회
            memberDTO = memberMapper.getMemberByUserId(existingOAuthUser.getUserId());
            
            // 회원 정보가 없거나 탈퇴한 회원인 경우
            if (memberDTO == null || "N".equals(memberDTO.getStatus())) {
                throw new OAuthProcessingException("탈퇴한 회원이거나 존재하지 않는 회원입니다.");
            }
            
            // 	사용자 정보 업데이트
            OAuthUserDTO updatedOAuthUser = OAuthUserDTO.builder()
                    .userId(existingOAuthUser.getUserId())
                    .provider(existingOAuthUser.getProvider())
                    .providerId(existingOAuthUser.getProviderId())
                    .email(kakaoUserInfo.getEmail())
                    .nickname(kakaoUserInfo.getNickname())
                    .build();
            
            oAuthMapper.updateOAuthUser(updatedOAuthUser);
            
            // 회원 테이블도 업데이트!!
            Member updatedMember = Member.builder()
                    .userId(memberDTO.getUserId())
                    .userName(kakaoUserInfo.getNickname())
                    .build();
            
            // 전화번호가 있는 경우에만 업데이트
            if (kakaoUserInfo.getPhoneNumber() != null && !kakaoUserInfo.getPhoneNumber().isEmpty()) {
                updatedMember = Member.builder()
                        .userId(memberDTO.getUserId())
                        .userName(kakaoUserInfo.getNickname())
                        .userPhone(kakaoUserInfo.getPhoneNumber())
                        .build();
            }
            
            memberMapper.updateMemberName(updatedMember);
            
            // 업데이트된 회원 정보 다시 조회!!
            memberDTO = memberMapper.getMemberByUserId(existingOAuthUser.getUserId());
        }
        
        
        Map<String, String> tokens = tokenService.generateToken(memberDTO.getUserEmail(), 
                                                              memberDTO.getUserId(), 
                                                              memberDTO.getRole());
        
        // 로그인 응답
        return OAuthLoginResponseDTO.builder()
                .accessToken(tokens.get("accessToken"))
                .refreshToken(tokens.get("refreshToken"))
                .userEmail(memberDTO.getUserEmail())
                .userName(memberDTO.getUserName())
                .userRole(memberDTO.getRole())
                .userPhone(memberDTO.getUserPhone())
                .isNewUser(isNewUser)
                .build();
    }
	
}
