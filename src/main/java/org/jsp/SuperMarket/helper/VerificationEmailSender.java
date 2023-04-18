package org.jsp.SuperMarket.helper;

import org.jsp.SuperMarket.dto.Customer;
import org.jsp.SuperMarket.dto.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class VerificationEmailSender {
	
	@Autowired
	JavaMailSender mailSender;

	public void sendEmail(Merchant merchant) {
//		every time these 2 steps are same while sending the message
		MimeMessage message=mailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message);
		
		try {
//			the mail in which ur send from
			helper.setFrom("rsnandeesh@gmail.com");
			helper.setTo(merchant.getEmail());
			helper.setSubject("Verification mail");
			helper.setText("Hello"+merchant.getName()+"Your OTP for Mail verification is "+merchant.getOtp());
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}
		mailSender.send(message);
	}
	
	public void sendEmail(Customer customer) {
//		every time these 2 steps are same while sending the message
		MimeMessage message=mailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message);
		
		try {
//			the mail in which ur send from
			helper.setFrom("rsnandeesh@gmail.com");
			helper.setTo(customer.getCust_email());
			helper.setSubject("Verification mail");
			helper.setText("Hello"+customer.getCust_name()+"Your OTP for Mail verification is "+customer.getOtp());
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}
		mailSender.send(message);
	}

}
