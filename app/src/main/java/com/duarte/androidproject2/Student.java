package com.duarte.androidproject2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Student implements Serializable {

    private String email;
    private List<Classes> classesList;
    private String docId;

    public Student(String email, String passWord) {
        this.email = email;
        this.classesList = new ArrayList<>();
    }

    public Student(HashMap<String, Object> objStudent, String docId){
        this.email = objStudent.get("email").toString();
        this.classesList = new ArrayList<>();
        this.docId = docId;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
