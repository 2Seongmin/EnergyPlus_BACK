package com.kh.ecolog.promise.model.service;

import org.springframework.stereotype.Service;

import com.kh.ecolog.auth.model.vo.CustomUserDetails;
import com.kh.ecolog.auth.service.AuthService;
import com.kh.ecolog.promise.model.dao.PromiseMapper;
import com.kh.ecolog.promise.model.dto.PromiseDTO;
import com.kh.ecolog.promise.vo.Promise;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PromiseServiceImpl implements PromiseService {

	private final PromiseMapper promiseMapper;
	private final AuthService authService;
	
	private void validateNotAlreadyRegistered(Long userId) {
		if (promiseMapper.findByUserId(userId) != null) {
			throw new IllegalStateException("이미 등록된 다짐이 있습니다.");
		}
	}

	private Promise toEntity(PromiseDTO dto, Long userId) {
		return Promise.builder()
		              .userPromise(dto.getUserPromise())
		              .userId(userId)
		              .build();
	}

	@Override
	public void insert(PromiseDTO promise) {
		Long userId = getCurrentUserId();

		validateNotAlreadyRegistered(userId);

		Promise entity = toEntity(promise, userId);
		promiseMapper.insert(entity);
	}

	@Override
	public void updateMyPromise(PromiseDTO promise) {
		promise.setUserId(getCurrentUserId());
		promiseMapper.updateByUserId(promise);
	}

	
	@Override
	public PromiseDTO selectMyPromise() {
		PromiseDTO promise = promiseMapper.findByUserId(getCurrentUserId());
		if(promise == null) {
			return new PromiseDTO();
		}
		return promise;
	}
	
	// userId
	private Long getCurrentUserId() {
		return authService.getUserDetails().getUserId();
	}

}
