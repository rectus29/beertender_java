package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.dao.impl.DaoHistory;
import com.rectus29.beertender.entities.core.History;
import com.rectus29.beertender.entities.core.User;
import com.rectus29.beertender.service.IserviceHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * User: rectus_29
 * Date: 4 févr. 2011
 * Time: 11:45:03
 */
@Service("serviceHistory")
public class ServiceHistory extends GenericManagerImpl<History, Long> implements IserviceHistory {
    private DaoHistory daoHistory;

    @Autowired
    public ServiceHistory(DaoHistory daoHistory) {
        super(daoHistory);
        this.daoHistory = daoHistory;
    }

    public History save(History c) {
        return daoHistory.save(c);
    }

    public History getLastHistory(User u, String a, Long i, String t) {
        return daoHistory.getLastHistory(u, a, i, t);
    }
    public List<History> getHistories(Date d, User u, String a, Long i, String t) {
        return daoHistory.getHistoriesByCriteria(d, u, a, i, t);
    }

    public List getHistories(int first, int count, String sortProperty, boolean sortAsc) {
        return daoHistory.getHistories(first, count, sortProperty, sortAsc);
    }
}
