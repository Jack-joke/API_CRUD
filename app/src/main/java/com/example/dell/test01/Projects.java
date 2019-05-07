package com.example.dell.test01;

import java.io.Serializable;
import java.util.Date;

public class Projects implements Serializable {
    private String name;
    private Date date, finishdate;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getFinishdate() {
        return finishdate;
    }

    public void setFinishdate(Date finishdate) {
        this.finishdate = finishdate;
    }

    public Projects() {
    }

    public Projects(int id, String name, Date date, Date finishdate) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.finishdate = finishdate;
    }
}
