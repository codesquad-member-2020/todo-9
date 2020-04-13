package kr.codesquad.todo9.error.exception;

import kr.codesquad.todo9.error.ErrorCode;

public class BoardNotFoundException extends EntityNotFoundException {

    public BoardNotFoundException() {
        super(ErrorCode.BOARD_NOT_FOUND);
    }

    public BoardNotFoundException(String message) {
        super(message, ErrorCode.BOARD_NOT_FOUND);
    }
}
