package com.rectus29.beertender.dao.impl;

import com.rectus29.beertender.entities.crest.Attribute;
import com.rectuscorp.evetool.dao.IdaoAttribute;
import com.rectuscorp.evetool.dao.IdaoConfig;
import com.rectuscorp.evetool.entities.core.Config;
import com.rectuscorp.evetool.entities.crest.Attribute;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: rectus_29
 * Date: 11 avr. 2010
 * Time: 00:34:16
 */
@Repository("daoAttribute")
public class DaoAttribute extends GenericDaoHibernate<Attribute, Long> implements IdaoAttribute {


    public DaoAttribute() {
        super(Attribute.class);
    }


}