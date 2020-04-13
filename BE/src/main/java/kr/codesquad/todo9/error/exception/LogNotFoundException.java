package kr.codesquad.todo9.error.exception;

import kr.codesquad.todo9.error.ErrorCode;

public class LogNotFoundException extends EntityNotFoundException {

    public LogNotFoundException() {
        super(ErrorCode.LOG_NOT_FOUND);
    }

    public LogNotFoundException(String message) {
        super(message, ErrorCode.LOG_NOT_FOUND);
    }
}
