package com.rectus29.beertender.dao.impl;

import com.rectus29.beertender.entities.crest.Region;
import com.rectuscorp.evetool.dao.IdaoRegion;
import com.rectuscorp.evetool.entities.crest.Region;
import org.springframework.stereotype.Repository;


/**
 * User: Rectus_29
 * Date: 10/02/16
 */
@Repository("daoRegion")
public class DaoRegion extends GenericDaoHibernate<Region, Long> implements IdaoRegion {


    public DaoRegion() {
        super(Region.class);
    }


}