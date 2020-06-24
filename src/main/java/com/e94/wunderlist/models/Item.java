package com.e94.wunderlist.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Item extends Auditor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemid;

    @Column(nullable = false)
    private String itemname;

    private boolean completed = false;

    private String recurring;


    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = "items", allowSetters = true)
    private User user;

    public Item() {
    }

    public Item(String itemname, boolean completed, String recurring, User user) {
        this.itemname = itemname;
        this.completed = completed;
        this.recurring = recurring;
        this.user = user;
    }

    public Item(String itemname, User user) {
        this.itemname = itemname;
        this.user = user;
    }


    public long getItemid() {
        return itemid;
    }

    public void setItemid(long itemid) {
        this.itemid = itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getRecurring() {
        return recurring;
    }

    public void setRecurring(String recurring) {
        this.recurring = recurring;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
