package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteForm {
    private Integer noteid;
    private String notetitle;
    private String notedescription;
    private Integer userId;
    private String userName;

    public NoteForm(Integer noteid, String notetitle, String notedescription, Integer userId, String userName) {
        this.noteid = noteid;
        this.notetitle = notetitle;
        this.notedescription = notedescription;
        this.userId = userId;
        this.userName = userName;
    }

    public NoteForm() {
    }

    public Integer getNoteid() {
        return noteid;
    }

    public void setNoteid(Integer noteid) {
        this.noteid = noteid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNotetitle() {
        return notetitle;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public String getNotedescription() {
        return notedescription;
    }

    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }
}
