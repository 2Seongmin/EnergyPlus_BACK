package com.kh.ecolog.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MemberEmailDuplicateException.class)
	public ResponseEntity<String> handleDuplicateMemberEmail(MemberEmailDuplicateException e){

		return new ResponseEntity<>("ERROR "+e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<String> handleInvalidTokenException(InvalidTokenException e){
		
		return new ResponseEntity<>("토큰오류 : "+e.getMessage(), HttpStatus.UNAUTHORIZED);
	}
	
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        log.error("사용자 없음: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("error", e.getMessage()));
    }
    
    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<?> handleCustomAuthenticationException(CustomAuthenticationException e) {
        log.error("인증 실패: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(Map.of("error", e.getMessage()));
    }
    
    @ExceptionHandler(OAuthProcessingException.class)
    public ResponseEntity<?> handleOAuthProcessingException(OAuthProcessingException e) {
        log.error("OAuth 처리 오류: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Map.of("error", e.getMessage()));
    }
    
    @ExceptionHandler(NoticeNotFoundException.class)
    public ResponseEntity<String> handleNoticeNotFound(NoticeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        					.body(ex.getMessage());
    }


}