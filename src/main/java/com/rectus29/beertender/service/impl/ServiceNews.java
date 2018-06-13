package com.rectus29.beertender.service.impl;


import com.rectus29.beertender.dao.impl.DaoNews;
import com.rectus29.beertender.entities.core.News;
import com.rectus29.beertender.service.IserviceNews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Oliv'Generator
 * User: rectus_29
 * Date: 03 sept. 2012
 * Time: 05:32:27
 */

@Service("serviceNews")
public class ServiceNews extends GenericManagerImpl<News, Long> implements IserviceNews {

    private DaoNews daoNews;

    @Autowired
    public ServiceNews(DaoNews daoNews) {
        super(daoNews);
        this.daoNews = daoNews;
    }


    public News save(News c) {
        return daoNews.save(c);
    }

    public List<News> getEnableAndPublishNewsFor(String t, String Id, Date dateFin, Date dateDeb){
        return daoNews.getEnableAndPublishNewsFor(t,Id, dateDeb, dateFin);
    }

 public List<News> getEnableAndPublishNews(Integer nbNews){
        return daoNews.getEnableAndPublishNews(nbNews);
    }
 public List<News> getEnableAndPublishNews(){

     return daoNews.getEnableAndPublishNews(null);
    }

}

