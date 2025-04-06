package com.fuchen.travel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author Fu chen
 * @date 2022/12/1
 * 邮件发送工具类
 */
@Component
public class MailClient {
	private static final Logger logger = LoggerFactory.getLogger(MailClient.class);
	
	private final JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String from;

	public MailClient(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMail(String to, String subject, String content){
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		try {
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, true);
			mailSender.send(helper.getMimeMessage());
		} catch (MessagingException e) {
			logger.error("发送邮件失败" + e.getMessage());
		}
	}

	
	
}
