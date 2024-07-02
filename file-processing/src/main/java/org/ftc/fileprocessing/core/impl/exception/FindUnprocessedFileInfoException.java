package org.ftc.fileprocessing.core.impl.exception;

public class FindUnprocessedFileInfoException extends FileInfoException {
    public FindUnprocessedFileInfoException(Throwable cause) {
        super("Error read file info", cause);
    }
}
