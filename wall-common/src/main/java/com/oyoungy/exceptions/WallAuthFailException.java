package com.oyoungy.exceptions;

public class WallAuthFailException extends WallBaseException {
    public WallAuthFailException() {
    }

    public WallAuthFailException(String message) {
        super(message);
    }

    public WallAuthFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public WallAuthFailException(Throwable cause) {
        super(cause);
    }

    public WallAuthFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
