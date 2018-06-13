package com.rectus29.beertender.dao.impl;

import com.rectus29.beertender.entities.core.Permission;
import com.rectus29.beertender.dao.IdaoPermission;
import com.rectus29.beertender.entities.core.Permission;
import org.springframework.stereotype.Repository;

/**
 * Created by Oliv'Generator
 * User: rectus_29
 * Date: 12 janv. 2012
 * Time: 11:32:36
 */

@Repository("daoPermission")
public class DaoPermission extends GenericDaoHibernate<Permission, Long> implements IdaoPermission {

    public DaoPermission() {
        super(Permission.class);
    }
}
