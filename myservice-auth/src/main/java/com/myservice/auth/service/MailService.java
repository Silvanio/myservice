package com.myservice.auth.service;

public interface MailService {

     void sendEmailHtml(String html,String subject, String...to) throws Exception;
}
