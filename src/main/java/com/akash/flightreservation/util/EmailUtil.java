package com.akash.flightreservation.util;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
	@Value("${com.akash.flightreservation.itinerary.email.body}")
	private String EMAIL_BODY ;
	@Value("${com.akash.flightreservation.itinerary.email.subject}")
	private String EMAIL_SUBJECT;
	@Autowired
	private JavaMailSender sender;
	private static final Logger LOGGER=LoggerFactory.getLogger(EmailUtil.class);
	public void sendItinerary(String toAddress,String filePath)
	{
		LOGGER.info("Inside sendItinerary()");
		try {
			MimeMessage message=sender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);// Here true indicates mail contains Multifile
			messageHelper.setTo(toAddress);
			messageHelper.setSubject(EMAIL_SUBJECT);
			messageHelper.setText(EMAIL_BODY);
			messageHelper.addAttachment("Itinearary", new File(filePath));
			sender.send(message);
		} catch (MessagingException e) {
			LOGGER.error("Exception inside sendItinerary "+e);
		}
		
	}

}
