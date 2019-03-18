package com.rectus29.beertender.entities.mail;

/*-----------------------------------------------------*/
/*                       Rectus29                      */
/*                Date: 12/08/2016 10:34               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import com.rectus29.beertender.entities.BasicGenericEntity;
import com.rectus29.beertender.entities.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "mailinglist", indexes = {
		@Index(name = "uniqueId", columnList = "uniqueId", unique = true)}
)
public class MailingList extends BasicGenericEntity<MailingList> {

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

	@Override
	public int compareTo(MailingList object) {
		return defaultCompareTo(object);
	}
}
