package org.ftc.fileprocessing.core.impl.exception;

public class FileInfoException extends RuntimeException {

    public FileInfoException(String message) {
        super(message);
    }

    public FileInfoException(String message, Throwable cause) {
        super(message, cause);
    }
}
