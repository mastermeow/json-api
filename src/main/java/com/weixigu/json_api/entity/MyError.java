package com.weixigu.json_api.entity;

//POJO; Customized error for failed HTTP requests.
public class MyError {
    private String error;

    public MyError(String error) {
        this.error = error;
    }

    public String getError() {
        return this.error;
    }
}
