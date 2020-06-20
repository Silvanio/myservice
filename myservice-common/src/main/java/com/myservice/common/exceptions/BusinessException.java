package com.myservice.common.exceptions;


public class BusinessException extends RuntimeException {

    String message;
    String detail;

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(String message, String detail) {
        super(message);
        this.message = message;
        this.detail = detail;
    }

    public BusinessException(String message, Throwable t) {
        super(message, t);
        this.message = message;
    }

    public BusinessException(String message, String detail, Throwable t) {
        super(message, t);
        this.message = message;
        this.detail = detail;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
