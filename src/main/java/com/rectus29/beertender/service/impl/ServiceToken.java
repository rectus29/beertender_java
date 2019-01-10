package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.Token;
import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.enums.TokenType;
import com.rectus29.beertender.service.IserviceToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * User: rectus_29
 * Date: 5 juil. 2010
 */

@Service("serviceToken")
public class ServiceToken extends GenericManagerImpl<Token, Long> implements IserviceToken {

	private static final Logger log = LogManager.getLogger(ServiceToken.class);

	public ServiceToken() {
		super(Token.class);
	}

	@Override
	public Token generateTokenFor(User user, TokenType tokenType) {
		Token token = new Token(tokenType);
		token.setObjectType(User.class.getName());
		token.setObjectId(user.getId());
		token = this.save(token);
		return token;
	}

	@Override
	public Token generateTokenFor(User user, TokenType tokenType, Date expirationDate) {
		Token token = new Token(tokenType);
		token.setObjectType(User.class.getName());
		token.setObjectId(user.getId());
		token.setExpirationDate(expirationDate);
		token = this.save(token);
		return token;
	}
}
