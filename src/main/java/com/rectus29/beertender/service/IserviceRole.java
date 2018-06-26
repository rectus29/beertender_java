package com.rectus29.beertender.service;

import com.rectus29.beertender.entities.Role;
import com.rectus29.beertender.entities.User;

import java.util.List;

/**
 * User: rectus_29
 * Date: 5 juil. 2010
 */
public interface IserviceRole extends GenericManager<Role, Long> {
    public Role getRoleByName(String roleName);

    public Role getRoleByDesc(String desc);

    public List<Role> getAuthorizedRole(User u);


}
