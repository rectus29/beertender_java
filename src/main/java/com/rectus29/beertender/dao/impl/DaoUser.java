package com.rectus29.beertender.dao.impl;

import com.rectus29.beertender.entities.core.User;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.dao.IdaoUser;
import com.rectus29.beertender.entities.core.User;
import com.rectus29.beertender.enums.State;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


/*-----------------------------------------------------*/
/* User: Rectus for          Date: 21/12/12 11:22 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

@Repository("daoUser")
public class DaoUser extends GenericDaoHibernate<User, Long> implements IdaoUser {


    public DaoUser() {
        super(User.class);
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
        List<User> result = (List<User>)getHibernateTemplate().findByCriteria(detachedCriteria);
        if (result.size() > 0) return result.get(0);
        return null;
    }

}
