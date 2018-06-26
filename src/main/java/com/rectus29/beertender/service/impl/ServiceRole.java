package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.Role;
import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.service.IserviceRole;
import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User: rectus_29
 * Date: 5 juil. 2010
 */

@Service("serviceRole")
public class ServiceRole extends GenericManagerImpl<Role, Long> implements IserviceRole {

    private static final Logger log = LogManager.getLogger(ServiceRole.class);


    public ServiceRole() {
        super(Role.class);
    }

    public Role getRoleByName(String roleName) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Role.class);
        detachedCriteria.add(Restrictions.eq("name", roleName));
        List result = getHibernateTemplate().findByCriteria(detachedCriteria);
        if (result.size() == 0)
            return null;
        return (Role) result.get(0);

    }

    public Role getRoleByDesc(String desc) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Role.class);
        detachedCriteria.add(Restrictions.eq("description", desc));
        List result = getHibernateTemplate().findByCriteria(detachedCriteria);
        if (result.size() == 0)
            return null;
        return (Role) result.get(0);

    }

    public List<Role> getAuthorizedRole(User u) {
        Role ref = u.getRole();
        List<Role> out = new ArrayList<Role>();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Role.class);
        detachedCriteria.add(Restrictions.ge("weight", ref.getWeight()));
        List result = getHibernateTemplate().findByCriteria(detachedCriteria);
        if (result.size() > 0)
            out.addAll(result);
        return out;

    }
}
