package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Subquery;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: rectus_29
 * Date: 20 janv. 2010
 * Time: 10:50:30
 * To change this template use File | Settings | File Templates.
 */
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
            // they are either authenticated or remembered from a previous com..mismastore.session,
            // so return the user:
            return get(id);
        } else {
            //not logged in or remembered:
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


    public User getUserByMail(String property) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
        detachedCriteria.add(Restrictions.eq("email", property));
        List<User> result = (List<User>) getHibernateTemplate().findByCriteria(detachedCriteria);
        if (result.size() > 0) return result.get(0);
        return null;
    }

}
