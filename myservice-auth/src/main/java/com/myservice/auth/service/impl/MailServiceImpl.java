package com.myservice.auth.service.impl;

import com.myservice.auth.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

@Service
@Transactional
public class MailServiceImpl implements MailService {

    @Autowired
    public JavaMailSender emailSender;

    public void sendEmailHtml(String html,String subject, String...to) throws Exception{
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false);
        mimeMessage.setContent(html, "text/html");
        message.setTo(to);
        message.setSubject(subject);
        emailSender.send(mimeMessage);
    }
}
