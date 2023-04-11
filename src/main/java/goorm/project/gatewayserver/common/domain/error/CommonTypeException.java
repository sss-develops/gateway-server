package goorm.project.gatewayserver.common.domain.error;

import goorm.project.gatewayserver.common.exception.common.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum CommonTypeException implements BaseExceptionType {


    NUMBER_FORMAT_EXCEPTION(400, "회원 아이디가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    LOGIN_FORBIDDEN_EXCEPTION(403, "일정 기간 내 로그인 시도 횟수를 초과했습니다.", HttpStatus.FORBIDDEN),
    NOT_FOUND_SESSION_MEMBER(403, "해당 세션의 멤버를 찾을 수 없습니다.", HttpStatus.FORBIDDEN),
    REDIS_WRONG_TYPE_DATASTRUCTURE_EXCEPTION(502, "올바르지 않은 키를 입력했습니다.", HttpStatus.BAD_GATEWAY),
    REDIS_CONNECTION_FAILURE_EXCEPTION(502, "서버에 문제가 발생했습니다.", HttpStatus.BAD_GATEWAY);

    private final int code;
    private final String message;
    private final HttpStatus status;

    CommonTypeException(
            int code,
            String message,
            HttpStatus status
    ) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public HttpStatus getStatus() {
        return null;
    }
}
