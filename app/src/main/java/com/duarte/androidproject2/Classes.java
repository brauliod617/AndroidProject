package com.duarte.androidproject2;

public class Classes {
    private String className;
    private String sectionNumber;

    Classes(String className, String classNumber){
        this.className = className;
        this.sectionNumber = classNumber;
    }
    Classes(){}

    public String getClassName() { return className; }

    public void setClassName(String className) { this.className = className; }

    public String getSectionNumber() { return sectionNumber; }

    public void setSectionNumber(String sectionNumber) { this.sectionNumber = sectionNumber; }

}
