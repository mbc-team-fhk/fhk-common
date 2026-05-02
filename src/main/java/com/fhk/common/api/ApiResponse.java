package com.fhk.common.api;

import org.springframework.http.ResponseEntity;

public final class ApiResponse {
	/**
	 * api request 성공 했을 때
	 * 돌려줄 조회값이 있는 경우
	 * ok 호출한다.
	 *
	 * @param body
	 * @param <T>
	 * @return
	 */
	public static <T> ResponseEntity<ApiWrapper<T>> ok(T body) {
		return ResponseEntity.ok(ApiWrapper.ok(body));
	}

	/**
	 * api request 성공 했을 때
	 * POST 를 통해 데이터 생성 요청인 경우
	 * created 호출한다.
	 *
	 * @param body
	 * @param <T>
	 * @return
	 */
	public static <T> ResponseEntity<ApiWrapper<T>> created(T body) {
		return ResponseEntity.status(201).body(ApiWrapper.created(body));
	}

	/**
	 * api request 성공 했을 때
	 * 돌려줄 조회값이 없는 경우
	 * noContent 호출한다.
	 *
	 * @return
	 */
	public static ResponseEntity<ApiWrapper<?>> noContent() {
		return ResponseEntity.status(204).body(ApiWrapper.none());
	}

	/**
	 * error 코드만 이용해서 httpState에 따른 message 리턴
	 *
	 * @return
	 */
	public static ResponseEntity<ApiWrapper<?>> error(int resCode) {
		return ResponseEntity.status(resCode).body(ApiWrapper.error(resCode));
	}

	/**
	 * error 코드 + 메세지 리턴
	 *
	 * @return
	 */
	public static ResponseEntity<ApiWrapper<?>> error(int resCode, String message) {
		return ResponseEntity.status(resCode).body(ApiWrapper.error(resCode, message));
	}
}
