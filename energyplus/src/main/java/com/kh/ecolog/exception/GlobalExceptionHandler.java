package com.kh.ecolog.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MemberEmailDuplicateException.class)
	public ResponseEntity< String> handleDuplicateMemberEmail(MemberEmailDuplicateException e){

		return new ResponseEntity<>("ERROR "+e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	

}