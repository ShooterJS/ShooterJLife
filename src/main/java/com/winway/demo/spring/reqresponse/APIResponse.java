package com.winway.demo.spring.reqresponse;


import lombok.Data;

@Data
public class APIResponse<T> {
    private boolean success;
    private T data;
    private int code;
    private String message;
}
