package com.myservice.auth.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Aspect
@Component
//TODO  classe criada devido a um erro no oauth, caso acessar de mais de um dispositivo ao mesmo tempo e fazer refreshtoken, irá aparecer DuplicateKey..Solução de contorno.
public class TokenEndpointRetryInterceptor {

     private static final int MAX_RETRIES = 4;

     @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.*(..))")
     public Object execute (ProceedingJoinPoint aJoinPoint) throws Throwable {
       int tts = 1000;
       for (int i=0; i<MAX_RETRIES; i++) {
         try {
           return aJoinPoint.proceed();
         } catch (DuplicateKeyException e) {
           Thread.sleep(tts);
           tts=tts*2;
         }
       }
       throw new IllegalStateException("Could not execute: " + aJoinPoint.getSignature().getName());
     }

}