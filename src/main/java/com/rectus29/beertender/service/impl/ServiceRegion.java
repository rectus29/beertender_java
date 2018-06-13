package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.crest.Region;
import com.rectuscorp.evetool.dao.impl.DaoRegion;
import com.rectuscorp.evetool.entities.crest.Region;
import com.rectuscorp.evetool.service.IserviceRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Rectus_29
 * Date: 10/02/16
 */
@Service("serviceRegion")
public class ServiceRegion extends GenericManagerImpl<Region, Long> implements IserviceRegion {

    private DaoRegion daoRegion;

    @Autowired
    public ServiceRegion(DaoRegion daoRegion) {
        super(daoRegion);
        this.daoRegion = daoRegion;
    }
}