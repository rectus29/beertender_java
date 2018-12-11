package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.enums.TokenType;
import com.rectus29.beertender.service.IserviceConfig;
import com.rectus29.beertender.service.IserviceMail;
import com.rectus29.beertender.service.IserviceToken;
import com.rectus29.beertender.service.IserviceUser;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.ui.freemarker.FreeMarkerTemplateUtils.processTemplateIntoString;


@Service("serviceMail")
public class ServiceMail implements IserviceMail, ServletContextAware {
	private final static String DEFAULTFROMMAIL = "";
	private final static String DEFAULTTOWOCMAIL = "";
	private JavaMailSender mailSender;
	private Configuration freemarkerConfiguration;
	private IserviceUser serviceUser;
	private IserviceToken serviceToken;
	private IserviceConfig serviceConfig;
	private ServletContext servletContext;

	@Autowired
	public ServiceMail(JavaMailSender mailSender, Configuration freemarkerConfiguration, IserviceUser serviceUser, IserviceConfig serviceConfig, IserviceToken serviceToken) {
		this.mailSender = mailSender;
		this.freemarkerConfiguration = freemarkerConfiguration;
		this.serviceUser = serviceUser;
		this.serviceConfig = serviceConfig;
		this.serviceToken = serviceToken;
	}

	public void sendEmail(final Long userId, final String subject, final String content) {
		final User user = serviceUser.get(userId);
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				message.setTo(serviceConfig.getByKey("assistanceDefaultMail").getValue());
				message.setFrom(DEFAULTFROMMAIL);
				message.setSubject(subject);
				HashMap model = new HashMap<String, Object>();
				model.put("user", user);
				model.put("content", content);
				String text = processTemplateIntoString(freemarkerConfiguration.getTemplate("stdMail.ftl"), model);
				message.setText(text, true);
			}
		};
		this.sendMail(preparator);
	}


	public void sendRestoreMail(final User user, final String session) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				message.setTo(user.getEmail());
				message.setFrom(DEFAULTFROMMAIL);
				message.setSubject("BeerTender - Restoration de votre mot de passe");
				Map model = new HashMap();
				model.put("user", user);
				model.put("uid", session);
				model.put("server_url", serviceConfig.getByKey("server_url").getValue());
				String text = processTemplateIntoString(freemarkerConfiguration.getTemplate("restorePasswordMail.ftl"), model);
				message.setText(text, true);
			}
		};
		this.sendMail(preparator);
	}

	public void trackMail(String token){

	}

	@Override
	public void sendEnrollmentMail(User enrollUser) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				message.setTo(enrollUser.getEmail());
				message.setFrom(DEFAULTFROMMAIL);
				message.setSubject("BeerTender - Bienvenue");
				Map model = new HashMap();
				model.put("user", enrollUser);
				model.put("trackingToken", serviceToken.generateTokenFor(enrollUser, TokenType.MAILTRACKINGTOKEN));
				model.put("mailToken", serviceToken.generateTokenFor(enrollUser, TokenType.SUBSCRIPTIONMAILTOKEN));
				model.put("server_url", serviceConfig.getByKey("server_url").getValue());
				String text = processTemplateIntoString(freemarkerConfiguration.getTemplate("enrollMail.ftl"), model);
				message.setText(text, true);
			}
		};
		this.sendMail(preparator);
	}

	private void sendMail(MimeMessagePreparator preparator){
		//this.save()
		this.mailSender.send(preparator);
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
