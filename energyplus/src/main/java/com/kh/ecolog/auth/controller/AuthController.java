package com.kh.ecolog.auth.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.ecolog.auth.model.dto.LoginDTO;
import com.kh.ecolog.auth.service.AuthService;
import com.kh.ecolog.auth.util.JWTUtil;
import com.kh.ecolog.member.model.dto.MemberDTO;
import com.kh.ecolog.member.model.service.MemberService;
import com.kh.ecolog.token.model.service.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;
    private final JWTUtil jwtUtil;
    private final MemberService memberService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO){
        try {
            Map<String, String> loginResponse = authService.login(loginDTO);
            log.info("로그인 응답: {}", loginResponse);
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            log.error("로그인 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> token){
        String refreshToken = token.get("refreshToken");
        
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "리프레시 토큰이 없습니다."));
        }
        
        try {
        	
            Long userId = jwtUtil.getUserIdFromToken(refreshToken);
            
            MemberDTO member = memberService.getMemberByUserId(userId);
            if ("N".equals(member.getStatus())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "탈퇴한 회원입니다."));
            }
        	
            Map<String, String> newToken = tokenService.refreshToken(refreshToken);
            return ResponseEntity.status(HttpStatus.CREATED).body(newToken);
        } catch (Exception e) {
            log.error("토큰 갱신 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(Map.of("error", e.getMessage()));
        }
    }
    
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(Map.of("message", "유효한 인증 정보가 없습니다."));
        }
        
        try {
            String token = authorizationHeader.substring(7);
            Long userId = jwtUtil.getUserIdFromToken(token);
            
            authService.logout(userId);
            
            return ResponseEntity.ok(Map.of("message", "로그아웃 되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(Map.of("error", "로그아웃 처리 중 오류가 발생했습니다."));
        }
    }
}