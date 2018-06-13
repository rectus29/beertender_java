package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.core.Config;
import com.rectus29.beertender.dao.impl.DaoConfig;
import com.rectus29.beertender.entities.core.Config;
import com.rectus29.beertender.service.IserviceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: rectus_29
 * Date: 16 mars 2010
 * Time: 11:25:42
 * To change this template use File | Settings | File Templates.
 */
@Service("serviceConfig")
public class ServiceConfig extends GenericManagerImpl<Config, Long> implements IserviceConfig {

    private DaoConfig daoConfig;

    @Autowired
    public ServiceConfig(DaoConfig daoConfig) {
        super(daoConfig);
        this.daoConfig = daoConfig;
    }


    public Config save(Config c) {
        return daoConfig.save(c);
    }

    public Config getByKey(String key) {
        return daoConfig.getByKey(key);
    }
}