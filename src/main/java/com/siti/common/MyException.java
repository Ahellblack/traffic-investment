package com.siti.common;

/**
 * Created by hongtu on 2018/6/21.
 * 自定义异常类
 */
public class MyException extends RuntimeException {

    public MyException() {
    }

    public MyException(String message) {
        super(message);
    }
}
