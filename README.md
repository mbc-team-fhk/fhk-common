# fhk-common

FHK Spring Boot 서비스들이 공통으로 사용하는 응답, 예외, 로깅 모듈입니다. 단독 실행 애플리케이션이 아니라 다른 서비스에 포함되는 `java-library` 모듈입니다.

## Purpose

- API 응답 포맷 통일
- 공통 예외 응답 처리
- 개발 환경 API request/response body 로깅
- pagination 응답 타입과 client info 추출 유틸 제공

## Provides

- `ApiResponse`: `ResponseEntity<ApiWrapper<T>>` 생성 헬퍼
- `ApiWrapper`: `isSuccess`, `resCode`, `resMessage`, `result` 공통 응답 body
- `GlobalExceptionHandler`: validation, JWT, 인증, 일반 예외를 공통 응답으로 변환
- `ApiBodyLoggingFilter`: staging/prod가 아닌 환경에서 request/response body 로깅
- `ClientInfo`: 요청 IP, user-agent, device id 추출
- `PagedRes`: 목록 응답 pagination wrapper

## Used By

- [fhk-security-server](https://github.com/mbc-team-fhk/fhk-security-server)
- [fhk-security-core](https://github.com/mbc-team-fhk/fhk-security-core)
- [fhk-ticket-reservation](https://github.com/mbc-team-fhk/fhk-ticket-reservation)

## Usage

현재 repo들은 배포된 Maven package 대신 빌드된 jar를 `libs/`에 두고 참조합니다.

```gradle
implementation files("libs/fhk-common-1.0.0-plain.jar")
```
