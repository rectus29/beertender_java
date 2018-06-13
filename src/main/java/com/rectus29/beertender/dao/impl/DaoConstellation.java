package com.rectus29.beertender.dao.impl;

import com.rectus29.beertender.entities.crest.Constellation;
import com.rectuscorp.evetool.dao.IdaoConstellation;
import com.rectuscorp.evetool.dao.IdaoRegion;
import com.rectuscorp.evetool.entities.crest.Constellation;
import com.rectuscorp.evetool.entities.crest.Region;
import org.springframework.stereotype.Repository;


/**
 * User: Rectus_29
 * Date: 10/02/16
 */
@Repository("daoConstellation")
public class DaoConstellation extends GenericDaoHibernate<Constellation, Long> implements IdaoConstellation {


    public DaoConstellation() {
        super(Constellation.class);
    }


}