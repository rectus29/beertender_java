package com.rectus29.beertender.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * User: Rectus
 * Date: 09/07/12
 */
@Entity
@Table(name = "news")
public class News extends GenericEntity<News> {

    @Column
    private String title;
    @Column(nullable = true, length = 65536)
    private String shortText;
    @Column(nullable = false, length = 65536)
    private String text;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;


    public News() {
        this.publishDate = new Date();
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int compareTo(News o) {
        return o.getPublishDate().compareTo(this.getPublishDate());
    }
}
