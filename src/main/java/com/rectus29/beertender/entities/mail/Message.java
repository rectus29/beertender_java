package com.rectus29.beertender.entities.mail;

import com.rectus29.beertender.entities.BasicGenericEntity;
import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.enums.MessageType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*-----------------------------------------------------*/
/*						rectus29					   */
/*                                                     */
/*                Date: 12/08/2016 14:21               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@Entity
@Table(name = "message", indexes = {
		@Index(name = "uniqueId", columnList = "uniqueId", unique = true)}
)
public class Message extends BasicGenericEntity<Message> {
	@Column
	private String subject;
	@Column
	private MessageType messageType = MessageType.NORM;
	@ManyToOne
	private User author;
	@OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
	private List<Recipient> recipientList = new ArrayList<Recipient>();
	@Column(columnDefinition = "MEDIUMTEXT", nullable = false, length = 524288)
	private String text;

	public Message() {
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public List<Recipient> getRecipientList() {
		return recipientList;
	}

	public void setRecipientList(List<Recipient> recipientList) {
		this.recipientList = recipientList;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
}
