package com.duarte.androidproject2;

import java.util.HashMap;
import java.util.Map;

public class Classes {
    private String className;
    private int classNumber;

    //Each student email will be unique, so it is key for map. Using map to ensure same student
    //doesn't register for same class more than once, and for fast indexing
    private Map<String, Student> studentMap;

    Classes(String className, int classNumber){
        this.className = className;
        this.classNumber = classNumber;
        studentMap = new HashMap<>();
    }

    public String getClassName() { return className; }

    public void setClassName(String className) { this.className = className; }

    public int getClassNumber() { return classNumber; }

    public void setClassNumber(int classNumber) { this.classNumber = classNumber; }

    public Map<String, Student> getStudentMap() { return studentMap; }

    public boolean addStudent(Student student) {
        //if student has already registered
        if(studentMap.containsKey(student.getEmail())){
            //do nothing return false
            return false;
        }else {
            //add student return true
            studentMap.put(student.getEmail(), student);
            return true;
        }
    }
}
