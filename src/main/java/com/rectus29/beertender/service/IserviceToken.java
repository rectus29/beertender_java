package com.rectus29.beertender.service;

import com.rectus29.beertender.entities.Token;
import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.enums.TokenType;

import java.util.Date;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 27/09/2018 14:41               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public interface IserviceToken extends GenericManager<Token, Long> {

	public Token generateTokenFor(User user, TokenType tokenType);

	public Token generateTokenFor(User user, TokenType tokenType, Date expirationDate);
}
