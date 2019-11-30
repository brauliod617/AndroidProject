package com.duarte.androidproject2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Classes {
    private String className;
    private String sectionNumber;
    private String classLocation;
    private List<String> listWeekdaysOfClass;
    private String startNEndtimeOfClass;
    private Boolean notification;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE", Locale.US);
    Date date = new Date();

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
            List<String> listWeekdaysOfClass, String startNEndtimeOfClass,
            Boolean notification){
        this.className = className;
        this.sectionNumber = classNumber;
        this.classLocation = classLocation;
        //FIXME: Not sure if this does deep copy, but that's what we need
        this.listWeekdaysOfClass = listWeekdaysOfClass;
        this.startNEndtimeOfClass = startNEndtimeOfClass;
        this.notification = notification;
    }
    Classes(){}

    public String getClassName() { return className; }

    public void setClassName(String className) { this.className = className; }

    public String getSectionNumber() { return sectionNumber; }

    public void setSectionNumber(String sectionNumber) { this.sectionNumber = sectionNumber; }

    public String getClassLocation() { return classLocation; }

    public void setClassLocation(String classLocation) { this.classLocation = classLocation; }

    public List<String> getListWeekdaysOfClass() { return listWeekdaysOfClass; }

    public Boolean getNotification() { return notification; }

    public void setNotification(Boolean notification) { this.notification = notification; }

    public void setListWeekdaysOfClass(List<String> listWeekdaysOfClass) {
        this.listWeekdaysOfClass = listWeekdaysOfClass;
    }

    public String getStartNEndtimeOfClass() { return startNEndtimeOfClass; }

    public void setStartNEndtimeOfClass(String startNEndtimeOfClass) {
        this.startNEndtimeOfClass = startNEndtimeOfClass;
    }
}
