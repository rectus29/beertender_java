package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("serviceUser")
public class ServiceUser extends GenericManagerImpl<User, Long> implements IserviceUser {

	public ServiceUser() {
		super(User.class);
	}

	public User getCurrentUser() {
		return getUser(SecurityUtils.getSubject());
	}

	public User getUser(Subject subject) {
		Long id = (Long) subject.getPrincipal();
		if (id != null) {
			return get(id);
		} else {
			return null;
		}
	}

	@Override
	public User getUserByUsername(String username) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
		detachedCriteria.add(Restrictions.eq("userName", username));
		detachedCriteria.add(Restrictions.eq("state", State.ENABLE));
		List result = getHibernateTemplate().findByCriteria(detachedCriteria);
		if (result.size() == 0)
			return null;
		return (User) result.get(0);
	}

	@Override
	public List<User> getAll() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
		List result = getHibernateTemplate().findByCriteria(detachedCriteria);
		return result;
	}

	@Override
	public List<User> getAll(List<State> stateArray) {
		return (List<User>) getHibernateTemplate()
				.findByCriteria(
						getDetachedCriteria()
								.add(Restrictions.in("state", stateArray)
								)
				);
	}

	public User getUserByMail(String property) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
		detachedCriteria.add(Restrictions.eq("email", property));
		List<User> result = (List<User>) getHibernateTemplate().findByCriteria(detachedCriteria);
		if (result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}

	}

}
