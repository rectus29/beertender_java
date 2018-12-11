package com.rectus29.beertender.entities;

import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.enums.TokenType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 11/12/2018 14:35               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@Entity
@Table(name = "token")
public class Token extends BasicGenericEntity<Token> {

	@Column(nullable = false)
	private TokenType tokenType;

	@Column(nullable = false)
	private State state = State.PENDING;

	@Column
	private String objectType;

	@Column
	private Long objectId;

	@Column(columnDefinition = "varchar(255)")
	private String token = UUID.randomUUID().toString();

	protected Token() {
	}

	public Token(TokenType tokenType) {
		this.tokenType = tokenType;
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public Token setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
		return this;
	}

	public State getState() {
		return state;
	}

	public Token setState(State state) {
		this.state = state;
		return this;
	}

	public String getObjectType() {
		return objectType;
	}

	public Token setObjectType(String objectType) {
		this.objectType = objectType;
		return this;
	}

	public Long getObjectId() {
		return objectId;
	}

	public Token setObjectId(Long objectId) {
		this.objectId = objectId;
		return this;
	}

	public String getToken() {
		return token;
	}

	public Token setToken(String token) {
		this.token = token;
		return this;
	}
}
