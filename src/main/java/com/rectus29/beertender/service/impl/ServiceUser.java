package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.core.User;
import com.rectuscorp.evetool.dao.impl.DaoUser;
import com.rectuscorp.evetool.entities.core.User;
import com.rectuscorp.evetool.service.IserviceUser;
import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.annotations.NamedQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private static final Logger log = LogManager.getLogger(ServiceUser.class);

    private DaoUser daoUser;

    @Autowired
    public ServiceUser(DaoUser daoUser) {
        super(daoUser);
        this.daoUser = daoUser;
    }


    public User getCurrentUser() {
        if (SecurityUtils.getSubject().getPrincipals().getRealmNames().contains("org.apache.shiro.realm.ldap.JndiLdapRealm_1")) {
            return getUserLDAP(SecurityUtils.getSubject());
        } else {
            return getUser(SecurityUtils.getSubject());
        }

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

    public User getUserLDAP(Subject subject) {
        String userName = (String) subject.getPrincipal();
        if (userName != null) {
            // they are either authenticated or remembered from a previous com..mismastore.session,
            // so return the user:
            return getUserByUsername(userName);
        } else {
            //not logged in or remembered:
            return null;
        }
    }

    public User getUserByUsername(String username) {
        return daoUser.getUserByUsername(username);
    }

    public User save(User c) {
        return daoUser.save(c);
    }

    public User getUserByMail(String property) {
        return daoUser.getUserByMail(property);
    }



}
