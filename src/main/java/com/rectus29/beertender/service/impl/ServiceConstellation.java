package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.crest.Constellation;
import com.rectus29.beertender.service.IserviceConstellation;
import com.rectuscorp.evetool.dao.impl.DaoConstellation;
import com.rectuscorp.evetool.entities.crest.Constellation;
import com.rectuscorp.evetool.service.GenericManager;
import com.rectuscorp.evetool.service.IserviceConstellation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Rectus_29
 * Date: 10/02/16
 */
@Service("serviceConstellation")
public class ServiceConstellation extends GenericManagerImpl<Constellation, Long> implements IserviceConstellation {

    private DaoConstellation daoConstellation;

    @Autowired
    public ServiceConstellation(DaoConstellation daoConstellation) {
        super(daoConstellation);
        this.daoConstellation = daoConstellation;
    }
}