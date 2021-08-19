package com.weixigu.json_api.exception_handler;

import com.weixigu.json_api.entity.MyError;
import com.weixigu.json_api.exception.MyInvalidServletRequestParameterException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Handles the exception thrown by missing required parameters
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<MyError> missingServletRequestParameterException(MissingServletRequestParameterException exception) {
        String paramName = exception.getParameterName();
        return ResponseEntity.status(400).body(new MyError(paramName + " parameter is required."));
    }

    @ExceptionHandler(MyInvalidServletRequestParameterException.class)
    public ResponseEntity<MyError> myInvalidServletRequestParameterException(MyInvalidServletRequestParameterException exception) {
        String message = exception.getMessage();
        return ResponseEntity.status(400).body(new MyError(message));
    }
}
