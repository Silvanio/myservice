package com.myservice.common.exceptions;

import com.myservice.common.dto.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> otherErrors(BusinessException ex){
        ErrorDTO errorDetails = new ErrorDTO(new Date(), ex.getMessage(), ex.getDetail());
        return ResponseEntity.badRequest().body(errorDetails);
    }

}