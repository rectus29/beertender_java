package com.rectus29.beertender.entities.mail;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 12/08/2016 10:34                */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import com.rectus29.beertender.entities.core.GenericEntity;
import com.rectus29.beertender.entities.core.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "mailinglist")
public class MailingList extends GenericEntity {

    @Column
    private String name;

    @ManyToMany(targetEntity = User.class)
    private List<User> usersList = new ArrayList<>();

    public MailingList() {
    }

    public String getName() {
        return name;
    }

    public MailingList setName(String name) {
        this.name = name;
        return this;
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }
}
