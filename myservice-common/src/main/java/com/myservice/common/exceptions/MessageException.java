package com.myservice.common.exceptions;

public enum MessageException {

    USER_NOT_FOUND("msg_user_not_found"),
    ERROR_SEND_MAIL("msg_error_send_mail");


    private String message;

    MessageException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
