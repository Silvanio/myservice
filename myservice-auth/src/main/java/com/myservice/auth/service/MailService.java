package com.myservice.auth.service;

import org.springframework.mail.SimpleMailMessage;

public interface MailService {

     void sendEmailHtml(String html,String subject, String...to) throws Exception;
}
