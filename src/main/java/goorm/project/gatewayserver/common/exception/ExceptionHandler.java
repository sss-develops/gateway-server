package goorm.project.gatewayserver.common.exception;

import goorm.project.gatewayserver.common.exception.common.ErrorResponse;
import goorm.project.gatewayserver.common.exception.common.LoginForbiddenException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;


@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse catchIllegalArgumentException(IllegalArgumentException exception) {
        return ErrorResponse.of(BAD_REQUEST, exception.getMessage());
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(LoginForbiddenException.class)
    public ErrorResponse catchLoginForbiddenException(LoginForbiddenException exception) {
        return ErrorResponse.of(FORBIDDEN, exception.getMessage());
    }
}
