package com.myservice.common.exceptions;

import com.myservice.common.dto.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> otherErrors(BusinessException ex) {
        ErrorDTO errorDetails = new ErrorDTO(new Date(), ex.getMessage(), ex.getDetail());
        return ResponseEntity.badRequest().body(errorDetails);
    }

   /* @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        ErrorDTO errorDetails = new ErrorDTO(new Date(), "", "");
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            String fieldName = ((FieldError) error).getField();
            errorDetails.setMessage(errorMessage);
            errorDetails.setDetails(errorMessage);
            errorDetails.setField(fieldName);
            return;
        });
        return ResponseEntity.badRequest().body(errorDetails);
    }*/

}