package com.rectus29.beertender.service;


import com.rectus29.beertender.entities.core.News;
import com.rectuscorp.evetool.entities.core.News;

import java.util.Date;
import java.util.List;

/**
 * Created by Oliv'Generator.
 * User: rectus_29
 * Date: 03 sept. 2012
 * Time: 05:32:27
 */

public interface IserviceNews extends GenericManager<News, Long> {

    public List<News> getEnableAndPublishNewsFor(String t, String Id, Date dateFin, Date dateDeb);
    public List<News> getEnableAndPublishNews(Integer nb);
    public List<News> getEnableAndPublishNews();

}