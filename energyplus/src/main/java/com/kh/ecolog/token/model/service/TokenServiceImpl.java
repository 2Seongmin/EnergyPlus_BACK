package com.kh.ecolog.token.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.ecolog.auth.util.JWTUtil;
import com.kh.ecolog.exception.InvalidTokenException;
import com.kh.ecolog.token.model.dao.TokenMapper;
import com.kh.ecolog.token.vo.RefreshToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {
	
	private final JWTUtil jwtUtil;
	private final TokenMapper tokenMapper;
	

	@Override
	@Transactional
	public Map<String, String> generateToken(String userEmail, Long userId) {
		
		// 액세스토큰과 리프레시토큰 생성
		Map<String, String> tokens = createTokens(userEmail);
		
		// 기존의 리프레시토큰 삭제
		tokenMapper.deleteTokenByUserId(userId);
		
		// 리프레시토큰을 DB에 저장
		saveRefreshToken(tokens.get("refreshToken"),userId);
		
		// 만료된 토큰 삭제
		tokenMapper.deleteExpiredRefreshToken(System.currentTimeMillis());
		
		
		return tokens;
	}
	
	/**
	 * 리프레시토큰을 DB에 저장
	 * @param refreshToken
	 * @param ussesrId
	 */
	private void saveRefreshToken(String refreshToken, Long userId) {
		
		// 리프레시토큰 유효기간 => 30일
		Long deadline = System.currentTimeMillis() + (3600000L * 24 * 30);
		
		RefreshToken token = RefreshToken.builder()
										 .userId(userId)
										 .token(refreshToken)
										 .deadline(deadline)
										 .build();
		
		tokenMapper.saveToken(token);
		log.info("리프레시토큰 저장 완료: 사용자 시퀀스넘버 = {}", userId);
		
	}
	
	
	/**
	 * 액세스토큰과 리프레시토큰 생성
	 * @param userEmail
	 * @return
	 */
	private Map<String, String> createTokens(String userEmail){
		
		String accessToken = jwtUtil.getAccessToken(userEmail);
		String refreshToken = jwtUtil.getRefreshToken(userEmail);
		
		Map<String, String> tokens = new HashMap<String, String>();
		tokens.put("accessToken", accessToken);
		tokens.put("refreshToken", refreshToken);
		
		return tokens;
	}
	
	

	@Override
	@Transactional
	public Map<String, String> refreshToken(String refreshToken) {
		
		
		try {
			// 리프레시토큰 검증
			Claims claims = jwtUtil.parseJwt(refreshToken);
			String userEmail = claims.getSubject();
			
			// DB에서 리프레시토큰 조회
			RefreshToken tokenEntity = tokenMapper.findByToken(refreshToken);
			
			if(tokenEntity == null) {
				throw new InvalidTokenException("존재하지 않는 리프레시 토큰입니다.");
			}
			
			// 토큰 유효기간 확인
			if (tokenEntity.getDeadline() < System.currentTimeMillis()) {
				tokenMapper.deleteTokenByUserId(tokenEntity.getUserId());
				throw new InvalidTokenException("만료된 리프레시 초큰입니다.");
			}
			
			return generateToken(userEmail, tokenEntity.getUserId());
			
			
		} catch (ExpiredJwtException e) {
			throw new InvalidTokenException("만료된 리프레시토큰 입니다.");
		} catch (Exception e) {
			throw new InvalidTokenException("유효하지 않은 리프레시 토큰입니다.");
		}
		
	}

	@Override
	@Transactional
	public void deleteUserToken(Long userId) {
		tokenMapper.deleteTokenByUserId(userId);
		log.info("사용자 시퀀스넘버 삭제 : {} ", userId);
		
	}

}
