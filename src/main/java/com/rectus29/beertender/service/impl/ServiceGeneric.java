package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.dao.impl.DaoGeneric;
import com.rectus29.beertender.entities.core.GenericEntity;
import com.rectus29.beertender.service.IserviceGeneric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Rectus_29
 * Date: 10/02/16
 */
@Service("serviceGeneric")
public class ServiceGeneric extends GenericManagerImpl<GenericEntity, Long> implements IserviceGeneric {

    private DaoGeneric daoGeneric;

    @Autowired
    public ServiceGeneric(DaoGeneric daoGeneric) {
        super(daoGeneric);
        this.daoGeneric = daoGeneric;
    }
}