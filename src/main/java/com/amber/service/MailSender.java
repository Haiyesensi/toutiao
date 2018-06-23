package com.amber.service;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Map;
import java.util.Properties;

@Service
public class MailSender implements InitializingBean {

    public static final String MAIL_PASSWD = "qie27ha8nnj";
    public static final String MAIL_NAME = "shihchang@163.com";
    public static final String MAIL_HOST = "smtp.163.com";
    public static final int MAIL_PORT = 465;
    public static final String MAIL_PROTOCOL = "smtps";


    private static Logger logger = LoggerFactory.getLogger(MailSender.class);
    private JavaMailSenderImpl mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    public boolean sendWithTemplate(String to, String subject, String template, Map<String, Object> model) {
        try {

            String nick = MimeUtility.encodeText("shihchang");
            InternetAddress from = new InternetAddress(nick + "<shihchang@163.com>");
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            String result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, "UTF-8", model);

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(result, true);

            mailSender.send(mimeMessage);
            return true;

        } catch (Exception e) {
            logger.error("send mail failure: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        mailSender = new JavaMailSenderImpl();
        mailSender.setUsername(MAIL_NAME);
        mailSender.setPassword(MAIL_PASSWD);
        mailSender.setHost(MAIL_HOST);
        mailSender.setPort(MAIL_PORT);
        mailSender.setProtocol(MAIL_PROTOCOL);
        mailSender.setDefaultEncoding("utf8");
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.ssl.enable", true);
        mailSender.setJavaMailProperties(javaMailProperties);
    }
}
