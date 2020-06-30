package com.myservice.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ResponseMessageDTO{
    String message;
    List<String> details;


    public static ResponseMessageDTO get(String message){
        return new ResponseMessageDTO(message);
    }

    public static ResponseMessageDTO get(String message, List<String> details){
        return new ResponseMessageDTO(message,details);
    }

    private ResponseMessageDTO(String message) {
        this.message = message;
    }
    private ResponseMessageDTO(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }
}
