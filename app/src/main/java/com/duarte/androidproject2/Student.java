package com.duarte.androidproject2;

public class Student {

    private String userName;
    private String email;
    /*TODO: Research on what is secure way to store passwords as opposed to plain text. Maybe something like aws secure store*/
    private String passWord;

    public Student(String userName, String email, String passWord) {
        this.userName = userName;
        this.email = email;
        this.passWord = passWord;
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

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
