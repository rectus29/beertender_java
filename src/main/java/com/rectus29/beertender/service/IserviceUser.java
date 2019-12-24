package com.rectus29.beertender.service;

import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.enums.State;
import org.apache.shiro.subject.Subject;

import java.util.List;


public interface IserviceUser extends GenericManager<User, Long> {

    User getCurrentUser();

    User getUser(Subject subject);

    User getUserByUsername(String groupName);

    User getUserByMail(String property);

	List<User> getAll(List<State> stateArray);
}
