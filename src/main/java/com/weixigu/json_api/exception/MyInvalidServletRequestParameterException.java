package com.weixigu.json_api.exception;

//Custom exception class; Throw it if http servlet request parameter ('tags', 'sortBy', 'direction') is invalid.
public class MyInvalidServletRequestParameterException extends Exception {
    public MyInvalidServletRequestParameterException() {
    }

    public MyInvalidServletRequestParameterException(String message) {
        super(message);
    }
}
