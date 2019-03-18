package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.entities.mail.Recipient;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceRecipient;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                   Date: 08/03/2019                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@Service("serviceRecipient")
public class ServiceRecipient extends GenericManagerImpl<Recipient, Long> implements IserviceRecipient {

	public ServiceRecipient() {
		super(Recipient.class);
	}

	@Override
	public Recipient save(Recipient c) {
		return super.save(c);
	}

	public List<Recipient> getUnreadFor(User u) {
		ArrayList<Recipient> out = new ArrayList<Recipient>();
		SessionFactory fact = this.getSessionFactory();
		Query query;
		query = fact.getCurrentSession().createQuery("FROM Recipient r WHERE r.target= :user AND r.readed= false AND r.state = :state");
		query.setParameter("user", u);
		query.setParameter("state", State.ENABLE);
		out.addAll(query.list());
		return out;
	}

	public List<Recipient> getAllFor(User u) {
		ArrayList<Recipient> out = new ArrayList<Recipient>();
		SessionFactory fact = this.getSessionFactory();
		Query query;
		query = fact.getCurrentSession().createQuery("FROM Recipient r WHERE r.target= :user");
		query.setParameter("user", u);
		out.addAll(query.list());
		return out;
	}
}
