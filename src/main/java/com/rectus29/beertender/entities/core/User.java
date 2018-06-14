package com.rectus29.beertender.entities.core;

import com.rectus29.beertender.entities.DecorableElement;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.enums.UserAuthentificationType;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
public class User extends GenericEntity implements DecorableElement{

    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String email;
    @Column
    private State state = State.ENABLE;
    @Column
    private String salt = new SecureRandomNumberGenerator().nextBytes(64).toBase64();
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Role role;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date restoreSessionDate;
    @Column
    private String restoreSession;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;
    @Column
    private UserAuthentificationType userAuthentificationType = UserAuthentificationType.EMBED;

    @Override
    public String getFormatedName() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean hasRole(String roleName) {
        return role.getName().equals(roleName);
    }

    public Date getRestoreSessionDate() {
        return restoreSessionDate;
    }

    public void setRestoreSessionDate(Date restoreSessionDate) {
        this.restoreSessionDate = restoreSessionDate;
    }

    public String getRestoreSession() {
        return restoreSession;
    }

    public void setRestoreSession(String restoreSession) {
        this.restoreSession = restoreSession;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getAvatarPath() {
        return null;
    }

    public boolean isAdmin() {
        if (this.role != null) {
            return this.role.getIsAdmin();
        }
        return false;
    }

    public UserAuthentificationType getUserAuthentificationType() {
        return userAuthentificationType;
    }

    public void setUserAuthentificationType(UserAuthentificationType userAuthentificationType) {
        this.userAuthentificationType = userAuthentificationType;
    }

}
