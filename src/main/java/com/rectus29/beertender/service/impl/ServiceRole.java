package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.core.Role;
import com.rectus29.beertender.entities.core.User;
import com.rectus29.beertender.dao.impl.DaoRole;
import com.rectus29.beertender.entities.core.Role;
import com.rectus29.beertender.entities.core.User;
import com.rectus29.beertender.service.IserviceRole;
import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: rectus_29
 * Date: 5 juil. 2010
 */

@Service("serviceRole")
public class ServiceRole extends GenericManagerImpl<Role, Long> implements IserviceRole {

    private static final Logger log = LogManager.getLogger(ServiceRole.class);

    private DaoRole daoRole;

    @Autowired
    public ServiceRole(DaoRole daoRole) {
        super(daoRole);
        this.daoRole = daoRole;
    }


    public Role getRoleByName(String roleName) {
        return daoRole.getRoleByName(roleName);
    }

    public Role getRoleByDesc(String desc) {
        return daoRole.getRoleByDesc(desc);
    }

    public List<Role> getAuthorizedRole(User u) {
        return daoRole.getAuthorizedRole(u);
    }
}
