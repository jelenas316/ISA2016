package com.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService{

	private final JavaMailSender javaMailSender;
	
	@Autowired
	public MailServiceImpl(final JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	@Override
	public void sendMail(String email, String message, String subject) throws MailException{
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom("nenaSchoolMail@gmail.com");
		mail.setSubject(subject);
		mail.setText(message);
		
		javaMailSender.send(mail);
		
	}

}
