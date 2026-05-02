package com.fhk.common.exception;

import com.fhk.common.api.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

	/**
	 * RequestBody DTO 바인딩 후 Valid 검증 실패
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		log.info("handleResponseStatus:{}", ex.getMessage());

		return ApiResponse.error(400, "invalid request");
	}

	/**
	 * RequestBody 객체 바인딩 실패
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
		log.info("handleResponseStatus:{}", ex.getMessage());

		return ApiResponse.error(400, "request body is missing or malformed");
	}

	/**
	 * 401 (Unauthorized)
	 * 403 (Forbidden)
	 * 404 (Not Found)
	 * 409 (Conflict)
	 * 500 (Internal Server Error)
	 */
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<?> handleResponseStatus(ResponseStatusException ex) {
		log.info("handleResponseStatus:{}", ex.getMessage());
		return ApiResponse.error(ex.getStatusCode().value());
	}

	/**
	 * DB 조회시 없는 결과
	 */
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<?> handleResponseStatus(NoSuchElementException ex) {
		log.info("handleResponseStatus:{}", ex.getMessage());
		return ApiResponse.error(404);
	}


	/**
	 * ====================================================================================
	 */

	/**
	 * 로그인 실패
	 * id or pw 불일치
	 */
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleResponseStatus(BadCredentialsException ex) {
		log.info("handleResponseStatus:{}", ex.getMessage());
		return ApiResponse.error(401, "invalid credentials");
	}

	/**
	 * Spring Security 인증 정보 만료
	 */
	@ExceptionHandler(CredentialsExpiredException.class)
	public ResponseEntity<?> handleResponseStatus(CredentialsExpiredException ex) {
		log.info("handleResponseStatus:{}", ex.getMessage());
		return ApiResponse.error(401, "credentials expired");
	}

	/**
	 * 토큰 유효기간 만료
	 */
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<?> handleExpiredJwtException(ExpiredJwtException ex) {
		log.info("handleExpiredJwtException: {}", ex.getMessage());
		return ApiResponse.error(401, "expired token");
	}

	/**
	 * 토큰 위조, 형식오류 등 예외상황
	 */
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<?> handleJwtException(JwtException ex) {
		log.info("handleJwtException: {}", ex.getMessage());
		return ApiResponse.error(401, "invalid token");
	}



	/**
	 * ====================================================================================
	 */

	/**
	 * 정의하지 않은 오류
	 * 500 (Internal Server Error)
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleAllExceptions(HttpServletRequest httpRequest, Exception e) throws Exception {
		log.info("handleAllExceptions:{}", e.getMessage());

		String uri = httpRequest.getRequestURI();
		if (uri.startsWith("/v3/api-docs") || uri.startsWith("/swagger-ui")) {
			throw e;
		}

		return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
}
