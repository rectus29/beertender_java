package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.core.Config;
import com.rectus29.beertender.service.IserviceConfig;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: rectus_29
 * Date: 16 mars 2010
 * Time: 11:25:42
 * To change this template use File | Settings | File Templates.
 */
@Service("serviceConfig")
public class ServiceConfig extends GenericManagerImpl<Config, Long> implements IserviceConfig {


    public ServiceConfig() {
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