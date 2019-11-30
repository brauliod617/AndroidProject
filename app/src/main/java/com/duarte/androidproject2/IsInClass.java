package com.duarte.androidproject2;

public class IsInClass {
    private String email;
    private String className;
    private String sectionNumber;

    IsInClass(){}
    IsInClass(String email, String className, String sectionNumber){
        this.email = email;
        this.className = className;
        this.sectionNumber = sectionNumber;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email;}

    public String getClassName() { return className; }

    public void setClassName(String className) { this.className = className; }

    public String getSectionNumber() { return sectionNumber; }

    public void setSectionNumber(String sectionNumber) { this.sectionNumber = sectionNumber; }
}
