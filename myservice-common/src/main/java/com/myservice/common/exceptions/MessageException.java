package com.myservice.common.exceptions;

import lombok.Getter;

@Getter
public enum MessageException {

    USER_NOT_FOUND("msg_user_not_found"),
    ERROR_SEND_MAIL("msg_error_send_mail"),
    MSG_REQUIRED_FIELDS("msg_required_fields"),
    MSG_INVALID_PASSWORD("msg_invalid_password"),
    MSG_GENERAL_ERROR("msg_general_error");

    private String message;

    MessageException(String message) {
        this.message = message;
    }

}
