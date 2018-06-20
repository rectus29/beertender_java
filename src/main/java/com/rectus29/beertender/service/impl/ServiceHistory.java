package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.core.History;
import com.rectus29.beertender.entities.core.User;
import com.rectus29.beertender.service.IserviceHistory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * User: rectus_29
 * Date: 4 févr. 2011
 * Time: 11:45:03
 */
@Service("serviceHistory")
public class ServiceHistory extends GenericManagerImpl<History, Long> implements IserviceHistory {


    public ServiceHistory() {
        super(History.class);
    }

    public List getHistories(Date d, User u, String a, Long i, String t) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(History.class);
        if (d != null)
            detachedCriteria.add(Restrictions.eq("date", d));
        if (u != null)
            detachedCriteria.add(Restrictions.eq("user", u));
        if (a != null)
            detachedCriteria.add(Restrictions.eq("action", a));

        if ((i != null) && (t != null)) {
            detachedCriteria.add(Restrictions.eq("objectId", i));
            detachedCriteria.add(Restrictions.eq("objectType", t));
        }
        List result = getHibernateTemplate().findByCriteria(detachedCriteria);
        if (result.size() == 0)
            return null;
        return result;
    }

    public List getHistories(int first, int count, String sortProperty, boolean sortAsc) {

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(History.class);
        if (sortAsc)
            detachedCriteria.addOrder(Order.asc(sortProperty));
        else
            detachedCriteria.addOrder(Order.desc(sortProperty));

        List result = getHibernateTemplate().findByCriteria(detachedCriteria, first, count);
        return result;
    }

    public History getLastHistory(User u, String a, Long i, String t) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(History.class);

        if (u != null)
            detachedCriteria.add(Restrictions.eq("user", u));
        if (a != null)
            detachedCriteria.add(Restrictions.eq("action", a));

        if ((i != null) && (t != null)) {
            detachedCriteria.add(Restrictions.eq("objectId", i));
            detachedCriteria.add(Restrictions.eq("objectType", t));
        }
        detachedCriteria.addOrder(Order.desc("date"));
        List result = getHibernateTemplate().findByCriteria(detachedCriteria);
        if (result.size() == 0)
            return null;
        return (History)result.get(0);
    }
}
