package kr.codesquad.todo9.responseobject;

public class Result {
    private boolean result;
    private String message;

    public Result(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
