package com.rectus29.beertender.entities.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: Rectus for
 * Date: 12/01/12
 * Time: 11:03
 */

@Entity
@Table(name="permission")
public class Permission extends GenericEntity implements Comparable<Permission> {

    @Column
    private String codeString;

    @Column
    private String description;

    public Permission() {
    }

    public String getCodeString() {
        return codeString;
    }

    public void setCodeString(String codeString) {
        this.codeString = codeString;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId().equals(((Permission) obj).getId());
    }

    @Override
    public int hashCode() {
        int result = codeString != null ? codeString.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public int compareTo(Permission o) {
        return this.getId().compareTo(o.getId());
    }
}
