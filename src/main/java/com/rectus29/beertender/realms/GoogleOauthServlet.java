package com.rectus29.beertender.realms;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.rectus29.beertender.service.IserviceSession;
import com.rectus29.beertender.service.impl.ServiceSession;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;

/*-----------------------------------------------------*/
/*                      Rectus29                       */
/*                Date: 21/09/2018 12:44               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@WebServlet(name = "googleAuthServlet", urlPatterns = "/googleOauthSignin")
public class GoogleOauthServlet extends HttpServlet {

	private Logger logger = LoggerFactory.getLogger(GoogleOauthServlet.class);
	
	protected IserviceSession serviceSession;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		process(req, resp);
	}

	@Transactional
	public void process(ServletRequest request, ServletResponse response) throws IOException {
		logger.debug("Hitting GoogleOauthServlet");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;


		if (httpRequest.getHeader("X-Requested-With") == null) {
			httpResponse.sendError(503, "Unauthorized call");
		}

		// Set path to the Web application client_secret_*.json file you downloaded from the
		// Google API Console: https://console.developers.google.com/apis/credentials
		// You can also find your Web application client ID and client secret from the
		// console and specify them directly when you create the GoogleAuthorizationCodeTokenRequest
		// object.
		String CLIENT_SECRET_FILE = "client_secret.json";

		//retreive authcode
		StringBuilder authCode = new StringBuilder();

		BufferedReader bufferedReader = request.getReader();
		char[] charBuffer = new char[128];
		int bytesRead = -1;
		while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
			authCode.append(charBuffer, 0, bytesRead);
		}

		// Exchange auth code for access token
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new FileReader(getClass().getClassLoader().getResource(CLIENT_SECRET_FILE).getFile()));
		GoogleTokenResponse tokenResponse =
				new GoogleAuthorizationCodeTokenRequest(
						new NetHttpTransport(),
						JacksonFactory.getDefaultInstance(),
						"https://www.googleapis.com/oauth2/v4/token",
						clientSecrets.getDetails().getClientId(),
						clientSecrets.getDetails().getClientSecret(),
						authCode.toString(),
						"postmessage"
				).execute();
		String accessToken = tokenResponse.getAccessToken();

		// Get profile info from ID token
		GoogleIdToken idToken = tokenResponse.parseIdToken();
		GoogleIdToken.Payload payload = idToken.getPayload();
		String userId = payload.getSubject();  // Use this value as a key to identify a user.
		String email = payload.getEmail();
		String name = (String) payload.get("name");
		String pictureUrl = (String) payload.get("picture");
		String familyName = (String) payload.get("family_name");
		String givenName = (String) payload.get("given_name");

		//retrieve user avatar from google
		byte[] avatarBytes = null;
		try {
			HttpClient client = new HttpClient();
			GetMethod get = new GetMethod(pictureUrl);
			int httpStatus = client.executeMethod(get);
			if (HttpStatus.SC_OK == httpStatus) {
				byte[] temp = get.getResponseBody();
				//check it's an image
				boolean isImage = ImageIO.read(new ByteArrayInputStream(temp)) != null;
				if (isImage) {
					avatarBytes = temp;
				}
			}
		} catch (IOException e) {
			logger.error("Error while retrieving user avatar from google", e);
		}
		logger.info("GoogleOauth user login: {}", email);

		final GoogleOauthToken token = new GoogleOauthToken(email)
				.setName(name)
				.setFamilyName(familyName)
				.setGivenName(givenName)
				.setUserId(userId)
				.setAvatarBytes(avatarBytes);
		Subject currentUser = SecurityUtils.getSubject();
		try {
			//retreive service from spring
			WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			ServiceSession serviceSession = (ServiceSession) webApplicationContext.getBean("serviceSession");
			//login the current user
			currentUser.login(token);
			serviceSession.addSubject(currentUser);
			logger.debug("Authorized user locally: {}", currentUser);
			httpResponse.setStatus(200);
			httpResponse.addHeader("auth", "Ok");
		} catch (Exception e) {
			logger.error("User cannot be authenticated. Probably not provisioned yet? Will respond with 401.", e);
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unknown user");
		}
	}
}
