package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.core.User;
import com.rectus29.beertender.entities.core.User;
import com.rectus29.beertender.service.IserviceSession;
import com.rectus29.beertender.service.IserviceUser;
import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __          | |             |__  / _      */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ / __| __| | | / __|   / / __, |    */
/*     | |    __/ (__| |_| |_| __   / /_   / /     */
/*     |_|  ____|___|__|__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 27/12/14 22:19                */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@Service("serviceSession")
public class ServiceSession implements IserviceSession {

    private static final Logger log = LogManager.getLogger(ServiceSession.class);
    private IserviceUser serviceUser;

    Set<Subject> subjectList = new HashSet<Subject>();

    @Autowired
    public ServiceSession(IserviceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    public void addSubject(Subject subject) {
        subjectList.add(subject);
    }

    public void onStop(Session session) {
        Subject subjectToRemove = null;
        for (Subject subject : subjectList) {
            /*if (subject.getSession().getId().equals(com..mismastore.session.getId())) {
                subjectToRemove = subject;
                User u = serviceUser.getUser(subject);
                log.debug(u.getLastName() + " vient de deco");
                break;
            } */
        }
        if (subjectToRemove != null)
            subjectList.remove(subjectToRemove);
    }

    public void onExpiration(Session session) {
        Subject subjectToRemove = null;
        for (Subject subject : subjectList) {
            if (subject.getSession().getId().equals(session.getId())) {
                subjectToRemove = subject;
                User u = serviceUser.getUser(subject);
                log.debug(u.getUserName() + " vient d'expirer");
                break;
            }
        }
        if (subjectToRemove != null)
            subjectList.remove(subjectToRemove);
    }

    public List<Subject> getConnectedSubjets() {
        cleanList();
        return new ArrayList<Subject>(subjectList);
    }

    private void cleanList() {
        for (Subject subject : subjectList) {
            try {
                subject.getSession().getLastAccessTime();
            } catch (Exception e) {
                subjectList.remove(subject);
            }
        }
    }
}
