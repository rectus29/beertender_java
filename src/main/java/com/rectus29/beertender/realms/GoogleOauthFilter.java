package com.rectus29.beertender.realms;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 21/09/2018 12:44               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class GoogleOauthFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(GoogleOauthFilter.class);
	public void init(FilterConfig filterConfig) throws ServletException {
		//nothing special here
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		logger.debug("Hitting GoogleOauthFilter");
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
			if(HttpStatus.SC_OK == httpStatus){
				byte[] temp  = get.getResponseBody();
				//check it's an image
				Boolean isImage = ImageIO.read(new ByteArrayInputStream(temp)) != null;
				if(isImage){
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
			currentUser.login(token);
			logger.debug("Authorized user locally: {}", currentUser);
			httpResponse.setStatus(200);
			httpResponse.addHeader("auth", "Ok");
			return;
		} catch (AuthenticationException e) {
			logger.error("User cannot be authenticated. Probably not provisioned yet? Will respond with 401.", e);
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unknown user");
			return;
		}
	}

	public void destroy() {
	}

}
