package com.rectus29.beertender.entities.mail;

import com.rectus29.beertender.entities.BasicGenericEntity;
import com.rectus29.beertender.entities.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                   Date: 08/03/2019                  */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
//@Entity
//@Table(name = "recipient", indexes = {
//		@Index(name = "uniqueId", columnList = "uniqueId", unique = true)}
//)
public class Recipient extends BasicGenericEntity<Recipient> {
	@Column
	private Boolean readed = false;
	@Column
	private Boolean archived = false;
	@ManyToOne(cascade = CascadeType.ALL)
	private Message message;
	@OneToOne
	private User target;
	@ManyToOne
	private Recipient parentRecipient;

	public Recipient() {
	}

	public Recipient(User target) {
		this.target = target;
	}

	public Recipient(User target, Message message) {
		this.target = target;
		this.message = message;
	}

	public Boolean getReaded() {
		return readed;
	}

	public void setReaded(Boolean readed) {
		this.readed = readed;
	}

	public Boolean getArchived() {
		return archived;
	}

	public void setArchived(Boolean archived) {
		this.archived = archived;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public User getTarget() {
		return target;
	}

	public void setTarget(User target) {
		this.target = target;
	}

	@Override
	public int compareTo(Recipient o) {
		if (o != null) {
			this.getCreated().compareTo(((Recipient) o).getCreated());
		}
		return -1;
	}

	public Recipient getParentRecipient() {
		return parentRecipient;
	}

	public void setParentRecipient(Recipient parentRecipient) {
		this.parentRecipient = parentRecipient;
	}
}
