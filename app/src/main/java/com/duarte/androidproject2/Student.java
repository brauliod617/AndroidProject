package com.duarte.androidproject2;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Student implements Serializable {

    private String userName;
    private String email;
    /*TODO: Research on what is secure way to store passwords as opposed to plain text. Maybe something like aws secure store*/
    private String passWord;
    private List<Classes> classesList;
    private String docId;

    public Student(String userName, String email, String passWord) {
        this.userName = userName;
        this.email = email;
        this.passWord = passWord;
        this.classesList = new ArrayList<>();
    }

    public Student(HashMap<String, Object> objStudent){
        this.userName = objStudent.get("userName").toString();
        this.email = objStudent.get("email").toString();
        this.passWord = objStudent.get("password").toString();
        this.classesList = new ArrayList<>();



        //TODO: fix these warnings
        if(objStudent.get("classesList") != null){

            ArrayList<HashMap<String, Object>> arrayList =
                    (ArrayList<HashMap<String, Object>>) objStudent.get("classesList");
            for(HashMap<String, Object> classMap : arrayList){
                Classes temp = new Classes(classMap);
                addToClassesList(temp);
            }
        }

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

    public void addToClassesList(Classes classes) { classesList.add(classes); }

    public boolean isInClass(Classes classes) {
        for(Classes classesFromList: classesList) {
            if(classesFromList.equals(classes)) {
                return true;
            }
        }
        return false;
    }

    public String getDocId() { return docId; }

    public void setDocId(String docId) { this.docId = docId; }
}
