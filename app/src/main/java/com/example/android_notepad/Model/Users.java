package com.example.android_notepad.Model;

public class Users {
    String tittle;
    String note;

    public Users() {
    }

    public Users(String tittle, String note) {
        this.tittle = tittle;
        this.note = note;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
