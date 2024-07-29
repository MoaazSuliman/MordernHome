package com.moaaz.modernhome.Mail;

import com.moaaz.modernhome.Order.Order;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import jakarta.mail.internet.MimeMessage;


import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class OrderMailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Configuration configuration;

//	@PostConstruct
//	private void initConfiguration() {
//		configuration = new Configuration(Configuration.VERSION_2_3_20);
//		configuration.setTemplateLoader(new ClassTemplateLoader(getClass(), "/freemarkers"));
//		configuration.setDefaultEncoding("UTF-8");
//		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
//
//	}

	@Async
	public void notifyUser(Order order) {
		sendMessage(order.getUser().getEmail(), "Your Order With Code '" + order.getCode() + "' Has Been Created", order.getUser().getName(), order.getCode(), "created");
	}


	@Async
	public void notifyUserOrderIsAccepted(Order order) {
		sendMessage(order.getUser().getEmail(), "Your Order With Code '" + order.getCode() + "' Has Been Accepted", order.getUser().getName(), order.getCode(), "accepted");
	}



	@Async
	public void notifyUserOrderIsCompleted(Order order) {
		sendMessage(order.getUser().getEmail(), "Your Order With Code '" + order.getCode() + "' Has Been Completed", order.getUser().getName(), order.getCode(), "completed");
	}

	private String processTemplate(String templateName, Map<String, Object> model)  {
		try{
			Template template = configuration.getTemplate(templateName);
			StringWriter writer = new StringWriter();
			template.process(model, writer);
			return writer.getBuffer().toString();
		} catch (TemplateException e) {
			throw new RuntimeException(e);
		} catch (TemplateNotFoundException e) {
			throw new RuntimeException(e);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		} catch (MalformedTemplateNameException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}


	}

	@Async
	public void sendMessage(String email, String subject, String userName, String orderCode, String orderStatus) {
		try {
			Map<String, Object> model = new HashMap<>();
			model.put("userName", userName);
			model.put("orderCode", orderCode);
			model.put("orderStatus", orderStatus);

			String content = processTemplate("OrderMailTemplate.ftl", model);

			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom("modernhomeinegypt@gmail.com");
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText(content, true); // 'true' indicates HTML content

			javaMailSender.send(message);
		} catch (Exception mailException) {
			throw new RuntimeException(mailException.getMessage());
		}
	}

}
