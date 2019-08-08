package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.service.IserviceSession;
import com.rectus29.beertender.service.IserviceUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/*-----------------------------------------------------*/
/*                     	Rectus29                       */
/*                Date: 27/12/14 22:19                 */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@Service("serviceSession")
public class ServiceSession implements IserviceSession {

    private static final Logger log = LogManager.getLogger(ServiceSession.class);
    private IserviceUser serviceUser;
	private HashMap<Serializable, Subject> subjectMap = new HashMap<>();

    @Autowired
    public ServiceSession(IserviceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    public void addSubject(Subject subject) {
		subjectMap.put(subject.getSession().getId(), subject);
		User u = serviceUser.getUser(subject);
		u.setLastLogin(new Date());
		serviceUser.save(u);
		log.debug(u.getFormattedName() + " just log in");
	}

	public void onStop(Session session) {
		if (subjectMap.containsKey(session.getId())) {
			User u = serviceUser.getUser(subjectMap.get(session.getId()));
			log.debug(u.getFormattedName() + " logout");
			subjectMap.remove(session.getId());
		}
	}

	public void onExpiration(Session session) {
		if (subjectMap.containsKey(session.getId())) {
			User u = serviceUser.getUser(subjectMap.get(session.getId()));
			log.debug(u.getFormattedName() + " has expired");
			subjectMap.remove(session.getId());
		}
	}

	public List<Subject> getConnectedSubjects() {
		cleanList();
		return new ArrayList<Subject>(subjectMap.values());
	}

	private void cleanList() {
		for (Subject subject : subjectMap.values()) {
			try {
				subject.getSession().getLastAccessTime();
			} catch (Exception e) {
				subjectMap.remove(subject);
			}
		}
	}
}
