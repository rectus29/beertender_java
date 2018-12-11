package com.rectus29.beertender.api.endpoint;

import com.google.api.client.http.HttpStatusCodes;
import com.rectus29.beertender.service.IserviceMail;
import com.rectus29.beertender.spring.AppContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/api/mailtracker.png", loadOnStartup = 3)
public class MailTrackerSerlvet extends HttpServlet {

	@Autowired
	private IserviceMail serviceMail;

	public MailTrackerSerlvet() {
		this.serviceMail = AppContext.getApplicationContext().getBean("serviceMail", IserviceMail.class);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		String mailToken = req.getParameter("mailToken");
		serviceMail.trackMail(mailToken);
		resp.setStatus(HttpStatusCodes.STATUS_CODE_OK);
	}

}
