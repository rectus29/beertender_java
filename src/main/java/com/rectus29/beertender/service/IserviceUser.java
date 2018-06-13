package com.rectus29.beertender.service;

import com.rectus29.beertender.entities.core.User;
import org.apache.shiro.subject.Subject;

/**
 * Created by IntelliJ IDEA.
 * User: rectus_29
 * Date: 20 janv. 2010
 * Time: 10:49:57
 */
public interface IserviceUser extends GenericManager<User, Long> {

    public User getCurrentUser();

    public User getUser(Subject subject);

    public User getUserByUsername(String groupName);

    public User getUserByMail(String property);

}
