package com.rectus29.beertender.dao.impl;

import com.rectus29.beertender.dao.IdaoNews;
import com.rectus29.beertender.entities.core.News;
import com.rectus29.beertender.enums.State;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Oliv'Generator
 * User: rectus_29
 * Date: 03 sept. 2012
 * Time: 05:32:27
 */

@Repository("daoNews")
public class DaoNews extends GenericDaoHibernate<News, Long> implements IdaoNews {

    public DaoNews() {
        super(News.class);
    }

    public List<News> getEnableAndPublishNewsFor(String t, String Id, Date dateFin, Date dateDeb) {
        List<News> result = new ArrayList<News>();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(News.class);
        detachedCriteria.add(Restrictions.eq("refType", t));
        if (Id != null)
            detachedCriteria.add(Restrictions.eq("refId", Id));
        detachedCriteria.add(Restrictions.le("publishDate", dateFin));
        detachedCriteria.add(Restrictions.ge("publishDate", dateDeb));
        detachedCriteria.add(Restrictions.eq("state", State.ENABLE));
        detachedCriteria.addOrder(Order.desc("publishDate"));
        List<News> out = (List<News>)getHibernateTemplate().findByCriteria(detachedCriteria);
        result.addAll(out);
        return result;
    }
    public List<News> getEnableAndPublishNews(Integer nbNews) {
        List<News> result = new ArrayList<News>();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(News.class);
        detachedCriteria.add(Restrictions.le("publishDate", new Date()));
        detachedCriteria.add(Restrictions.eq("state", State.ENABLE));
        detachedCriteria.addOrder(Order.desc("publishDate"));
        if (nbNews != null)
            result = (List<News>)getHibernateTemplate().findByCriteria(detachedCriteria, 0, nbNews);
        else
            result =(List<News>)getHibernateTemplate().findByCriteria(detachedCriteria);
        return result;
    }

}

