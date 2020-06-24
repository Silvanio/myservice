package com.myservice.common.exceptions;

public enum MessageException {

    USER_NOT_FOUND("msg_user_not_found"),
    ERROR_SEND_MAIL("msg_error_send_mail"),
    MSG_REQUIRED_FIELDS("msg_required_fields"),
    MSG_INVALID_PASSWORD("msg_invalid_password");


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
