package com.rectus29.beertender.service;

import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.enums.State;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: rectus_29
 * Date: 20 janv. 2010
 * Time: 10:49:57
 */
public interface IserviceUser extends GenericManager<User, Long> {

    User getCurrentUser();

    User getUser(Subject subject);

    User getUserByUsername(String groupName);

    User getUserByMail(String property);

	List<User> getAll(List<State> stateArray);

	void disable(User modelObject);
}
