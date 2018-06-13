package com.rectus29.beertender.service;


import com.rectus29.beertender.entities.core.Config;
import com.rectuscorp.evetool.entities.core.Config;

/**
 * Created by IntelliJ IDEA.
 * User: rectus_29
 * Date: 16 mars 2010
 * Time: 11:25:01
 * To change this template use File | Settings | File Templates.
 */
public interface IserviceConfig extends GenericManager<Config, Long> {

    Config getByKey(String key);
}