package com.duarte.androidproject2;

import java.util.ArrayList;
import java.util.List;

public class Classes {
    private String className;
    private int classNumber;
    private List<Student> studentList;

    Classes(String className, int classNumber){
        this.className = className;
        this.classNumber = classNumber;
        studentList = new ArrayList<>();
    }

    public String getClassName() { return className; }

    public void setClassName(String className) { this.className = className; }

    public int getClassNumber() { return classNumber; }

    public void setClassNumber(int classNumber) { this.classNumber = classNumber; }

    public List<Student> getStudentList() { return studentList; }

    public boolean addStudent(Student student) {

    }
}
