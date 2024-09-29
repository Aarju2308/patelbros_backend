package com.patelbros.services;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.patelbros.entities.Cart;
import com.patelbros.enums.EmailTemplates;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final SpringTemplateEngine templateEngine;
	private final JavaMailSender mailSender;
	
	
	@Async
	public void sendMail(
			String to,
			String username,
			EmailTemplates emailTemplate,
			String activationCode,
			String subject
	) throws MessagingException {
		
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(
				mimeMessage,
				MimeMessageHelper.MULTIPART_MODE_MIXED,
				StandardCharsets.UTF_8.name()
		);
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("username", username);
		properties.put("confirmationUrl", "localhost:4200/verify-account?validatinOtp="+activationCode);
		properties.put("activation_code", activationCode);
		
		Context context = new Context();
		context.setVariables(properties);
		
		messageHelper.setTo(to);
		messageHelper.setFrom("support@patelbros.com");
		messageHelper.setSubject(subject);
		
		String template = templateEngine.process(emailTemplate.getName(), context);
		messageHelper.setText(template, true);

		mailSender.send(mimeMessage);
	}
	
	@Async
	public void sendOrderConfirmationMail(
			String to,
			String userName,
			String billNo,
			double totalAmount,
			List<Cart> carts
			) throws MessagingException {
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(
				mimeMessage,
				MimeMessageHelper.MULTIPART_MODE_MIXED,
				StandardCharsets.UTF_8.name());
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("billNo", billNo);
		properties.put("customerName", userName);
		properties.put("totalAmount", totalAmount);
		properties.put("carts", carts);
		
		Context context = new Context();
		context.setVariables(properties);
		
		helper.setTo(to);
		helper.setFrom("support@patelbros.com");
		helper.setSubject("Order Confirmation");
		
		String template = templateEngine.process(EmailTemplates.ORDER_CONFIRMATION.getName(), context);
		helper.setText(template,true);
		
		mailSender.send(mimeMessage);
	}
}
