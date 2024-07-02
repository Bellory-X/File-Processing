package org.ftc.fileprocessing.app;

import org.ftc.fileprocessing.core.impl.exception.FileInfoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {FileInfoController.class})
public class FileInfoExceptionHandler {

    @ExceptionHandler(FileInfoException.class)
    public ResponseEntity<ProblemDetail> handleException(FileInfoException e) {
        return new ResponseEntity<>(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
