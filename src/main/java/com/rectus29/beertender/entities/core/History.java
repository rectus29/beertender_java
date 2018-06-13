package com.rectus29.beertender.entities.core;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: rectus_29
 * Date: 4 févr. 2011
 * Time: 11:03:02
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "history")
public class History implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;


    @Column(name = "action", nullable = false, length = 65536)
    private String action = "";

    @Column(name = "object_type", nullable = false, length = 65536)
    private String objectType;

    @Column(name = "object_id", nullable = false)
    private Long objectId;

    @Column(name = "details", nullable = false, length = 65536)
    private String details = "";

    @Column(name="ip")
    private String ip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public History() {
    }

    public History(String action, Date date, String details, String ip, Long objectId, String objectType, User user) {
        this.action = action;
        this.date = date;
        this.details = details;
        this.ip = ip;
        this.objectId = objectId;
        this.objectType = objectType;
        this.user = user;
    }
    public History(String action, String details, String ip, Long objectId, String objectType, User user) {
        this.action = action;
        this.date = new Date();
        this.details = details;
        this.ip = ip;
        this.objectId = objectId;
        this.objectType = objectType;
        this.user = user;
    }

    public History(Date date, User user, String action, Long id, String type, String details) {
        this.date = date;
        this.details = details;
        this.action = action;
        this.user = user;
        this.objectId=id;
        this.objectType=type;
    }
    public History(Date date, User user, String action, Long id, String type) {
        this.date = date;
        this.action = action;
        this.user = user;
        this.objectId=id;
        this.objectType=type;
    }
    public History(User user, String action, Long id, String type, String details) {
        this.date = new Date();
        this.action = action;
        this.user = user;
        this.objectId = id;
        this.objectType=type;
        this.details = details;
    }
    public History(User user, String action, Long id, String type) {
        this.date = new Date();
        this.action = action;
        this.user = user;
        this.objectId = id;
        this.objectType=type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
