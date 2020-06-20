package com.myservice.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ResponseMessageDTO {
    String message;
    List<String> details;

    public ResponseMessageDTO(String message) {
        this.message = message;
    }
    public ResponseMessageDTO(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }
}
