package com.oyoungy.exceptions;

public class WallNotFoundException extends WallBaseException {
    public WallNotFoundException() {
    }

    public WallNotFoundException(String message) {
        super(message);
    }

    public WallNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WallNotFoundException(Throwable cause) {
        super(cause);
    }

    public WallNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
