package com.duarte.androidproject2;

public class Classes {
    private String className;
    private String sectionNumber;
    private String classLocation;
    private String classDay;
    private Boolean notification;

    //TODO: This constructor is being used to populate the database from "Add class dialog"
    //      Need to add function call to pull all the needed info from db and populate this
    //      this class. See following comment

    Classes(String className, String classNumber){
        this.className = className;
        this.sectionNumber = classNumber;
    }

    //TODO: Here is where things will get tricky, we need to get a list of all CS classes, with all
    //      following information, populate a database with it, and use that to populate this
    //      class, than check that each class user tries to create matches a class in the db
    //      than pull from the classes db tuple the following information to populate this class.
    //      This constructor is used to show in the "Home page"

    Classes(String className, String classNumber, String classLocation,
            String classDay,
            Boolean notification){
        this.className = className;
        this.sectionNumber = classNumber;
        this.classLocation = classLocation;
        this.notification = notification;
        this.classDay = classDay;
    }
    Classes(){}

    public String getClassName() { return className; }

    public void setClassName(String className) { this.className = className; }

    public String getSectionNumber() { return sectionNumber; }

    public void setSectionNumber(String sectionNumber) { this.sectionNumber = sectionNumber; }

    public String getClassLocation() { return classLocation; }

    public void setClassLocation(String classLocation) { this.classLocation = classLocation; }

    public Boolean getNotification() { return notification; }

    public void setNotification(Boolean notification) { this.notification = notification; }

    public String getClassDay() { return classDay; }

    public void setClassDay(String classDay) { this.classDay = classDay; }
}
