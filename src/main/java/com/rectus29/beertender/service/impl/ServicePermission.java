package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.Permission;
import com.rectus29.beertender.service.IservicePermission;
import org.springframework.stereotype.Service;

/**
 * Created by Oliv'Generator
 * User: rectus_29
 * Date: 12 janv. 2012
 * Time: 11:32:36
 */

@Service("servicePermission")
public class ServicePermission extends GenericManagerImpl<Permission, Long> implements IservicePermission {


    public ServicePermission() {
        super(Permission.class);
    }

    public Permission save(Permission c) {
        return this.save(c);
    }

}

