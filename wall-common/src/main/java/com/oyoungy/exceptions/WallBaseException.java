package com.oyoungy.exceptions;

public class WallBaseException extends RuntimeException {
    public WallBaseException() {
    }

    public WallBaseException(String message) {
        super(message);
    }

    public WallBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public WallBaseException(Throwable cause) {
        super(cause);
    }

    public WallBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
