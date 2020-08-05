package com.myservice.auth.config;

import com.myservice.common.exceptions.ExceptionHandlerAdvice;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class MyExceptionHandler extends ExceptionHandlerAdvice {}