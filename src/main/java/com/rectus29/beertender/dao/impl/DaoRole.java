package com.rectus29.beertender.dao.impl;

import com.rectus29.beertender.dao.IdaoRole;
import com.rectus29.beertender.entities.core.Role;
import com.rectus29.beertender.entities.core.User;
import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


/*-----------------------------------------------------*/
/* User: Rectus for          Date: 21/12/12 11:22 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

@Repository("daoRole")
public class DaoRole extends GenericDaoHibernate<Role, Long> implements IdaoRole {

    private static final Logger log = LogManager.getLogger(DaoRole.class);

    public DaoRole() {
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
