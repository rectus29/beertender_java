package com.rectus29.beertender.service;

import com.rectus29.beertender.entities.History;
import com.rectus29.beertender.entities.User;

import java.util.Date;
import java.util.List;

/**
 * User: rectus_29
 * Date: 4 févr. 2011
 * Time: 11:44:48
 */
public interface IserviceHistory extends  GenericManager<History, Long> {
    public List<History> getHistories(Date d, User u, String a, Long i, String t);
    public List getHistories(int first, int count, String sortProperty, boolean sortAsc);
    public History getLastHistory(User u, String a, Long i, String t);
}
