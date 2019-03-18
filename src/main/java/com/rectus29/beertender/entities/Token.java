package com.rectus29.beertender.entities;

import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.enums.TokenType;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 11/12/2018 14:35               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@Entity
@Table(name = "token", indexes = {
		@Index(name = "uniqueId", columnList = "uniqueId", unique = true)}
)
public class Token extends BasicGenericEntity<Token> {

	@Column(nullable = false, length = 50)
	@Enumerated(EnumType.STRING)
	private TokenType tokenType;

	@Column
	private String objectType;

	@Column
	private Long objectId;

	@Column(columnDefinition = "varchar(255)")
	private String token = UUID.randomUUID().toString();

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate;

	protected Token() {
		this.state = State.PENDING;
	}

	public Token(TokenType tokenType) {
		this();
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

	public Date getExpirationDate() {
		return expirationDate;
	}

	public Token setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
		return this;
	}

	public Boolean isExpired(){
		if(this.state != State.DISABLE && this.state != State.DELETED){
			return (this.getExpirationDate() != null) && this.getExpirationDate().after(new Date());
		}
		return true;
	}
}
