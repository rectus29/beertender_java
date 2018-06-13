package com.rectus29.beertender.dao.impl;

import com.rectus29.beertender.entities.core.Config;
import com.rectus29.beertender.dao.IdaoConfig;
import com.rectus29.beertender.entities.core.Config;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: rectus_29
 * Date: 11 avr. 2010
 * Time: 00:34:16
 * To change this template use File | Settings | File Templates.
 */
@Repository("daoConfig")
public class DaoConfig extends GenericDaoHibernate<Config, Long> implements IdaoConfig {


    public DaoConfig() {
        super(Config.class);
    }


    public Config getByKey(String key) {

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Config.class);
        detachedCriteria.add(Restrictions.eq("key", key));
        List result = getHibernateTemplate().findByCriteria(detachedCriteria);
        if (result.size() == 0)
            return null;
        return (Config) result.get(0);
    }

}