package com.myservice.common.exceptions;

import lombok.Getter;

@Getter
public enum MessageException {

    USER_NOT_FOUND("msg_user_not_found"),
    ERROR_SEND_MAIL("msg_error_send_mail"),
    MSG_REQUIRED_FIELDS("msg_required_fields"),
    MSG_INVALID_PASSWORD("msg_invalid_password"),
    MSG_GENERAL_ERROR("msg_general_error"),
    MSG_GENERAL_VALIDATE("msg_general_validate"),
    MSG_PERIOD_DATE_INVALID("msg_period_date_invalid"),
    MSG_USERNAME_EXIST("msg_username_exist"),
    MSG_CONTRACT_EXPIRED("msg_contract_expired"),
    MSG_CONTRACT_LIMIT_USER("msg_contract_limit_user"),
    MSG_GENERAL_UPLOAD_ERROR("msg_general_upload_error");



    private String message;

    MessageException(String message) {
        this.message = message;
    }

}
