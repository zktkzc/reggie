package com.reggie.common;

/**
 * 自定义业务异常
 *
 * @author tkzc
 */
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
