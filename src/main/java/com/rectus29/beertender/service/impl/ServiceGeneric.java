package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.core.GenericEntity;
import com.rectus29.beertender.service.IserviceGeneric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * User: Rectus_29
 * Date: 10/02/16
 */
@Service("serviceGeneric")
public class ServiceGeneric<T> extends GenericManagerImpl<GenericEntity, Long> implements IserviceGeneric {



    public ServiceGeneric() {
        super(GenericEntity.class);
    }

    @Override
    public GenericEntity save(GenericEntity object) {
        object.setUpdated(new Date());
        return super.save(object);
    }

    public List<T> getAll(Class<T> persistentClass) {
        return hibernateTemplate.loadAll(persistentClass);
    }
}