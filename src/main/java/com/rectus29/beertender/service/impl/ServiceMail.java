package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.service.IserviceConfig;
import com.rectus29.beertender.service.IserviceMail;
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
	private final static String DEFAULTFROMWOCMAIL = "woc@andil.fr";
	private final static String DEFAULTTOWOCMAIL = "woc@andil.fr";
	private JavaMailSender mailSender;
	private Configuration freemarkerConfiguration;
	private IserviceUser serviceUser;
	private IserviceConfig serviceConfig;
	private ServletContext servletContext;

	@Autowired
	public ServiceMail(JavaMailSender mailSender, Configuration freemarkerConfiguration, IserviceUser serviceUser, IserviceConfig serviceConfig) {
		this.mailSender = mailSender;
		this.freemarkerConfiguration = freemarkerConfiguration;
		this.serviceUser = serviceUser;
		this.serviceConfig = serviceConfig;
	}

	public void sendEmail(final Long userId, final String subject, final String content) {
		final User user = serviceUser.get(userId);
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				message.setTo(serviceConfig.getByKey("assistanceDefaultMail").getValue());
				message.setFrom(DEFAULTFROMWOCMAIL);
				message.setSubject(subject);
				HashMap model = new HashMap<String, Object>();
				model.put("user", user);
				model.put("content", content);
				String text = processTemplateIntoString(freemarkerConfiguration.getTemplate("stdMail.ftl"), model);
				message.setText(text, true);
			}
		};
		this.mailSender.send(preparator);
	}


	public void sendRestoreMail(final User user, final String session) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				message.setTo(user.getEmail());
				message.setFrom(DEFAULTFROMWOCMAIL);
				message.setSubject("BeerTender - Restoration de votre mot de passe");
				Map model = new HashMap();
				model.put("user", user);
				model.put("uid", session);
				model.put("server_url", serviceConfig.getByKey("server_url").getValue());
				String text = processTemplateIntoString(freemarkerConfiguration.getTemplate("restorePasswordMail.ftl"), model);
				message.setText(text, true);
			}
		};
		this.mailSender.send(preparator);
	}

	@Override
	public void sendEnrolmentMail(User enrollUser) {

	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
