package com.rectus29.beertender.service;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * User: rectus_29
 * Date: 9 févr. 2011
 */
public interface IserviceSession {

    public void addSubject(Subject subject);
    public void onStop(Session session);
    public void onExpiration(Session session);

	public List<Subject> getConnectedSubjects();
}
