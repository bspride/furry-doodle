package com.quantum.apps.simplynotes;

/**
 * Created by mgclarke on 6/5/17.
 */

public class Note {
    private long id;
    private String title;
    private String editDate;
    private String createDate;
    private String data;

    public Note() {
        this.title = "";
        this.editDate = "";
        this.createDate = "";
        this.data = "";
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }

    public String getEditDate() {
        return this.editDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateDate() { return this.createDate; }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return this.data;
    }
}
