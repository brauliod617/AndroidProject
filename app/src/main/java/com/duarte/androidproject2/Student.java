package com.duarte.androidproject2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Student implements Serializable {

    private String userName;
    private String email;
    /*TODO: Research on what is secure way to store passwords as opposed to plain text. Maybe something like aws secure store*/
    private String passWord;
    private List<Classes> classesList;

    public Student(String userName, String email, String passWord) {
        this.userName = userName;
        this.email = email;
        this.passWord = passWord;
        this.classesList = new ArrayList<>();
    }

    public Student(){}

    public Student(HashMap<String, Object> objStudent){
        this.userName = objStudent.get("userName").toString();
        this.email = objStudent.get("email").toString();
        this.passWord = objStudent.get("password").toString();
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

    public List<Classes> getClassesList() { return classesList; }
}
