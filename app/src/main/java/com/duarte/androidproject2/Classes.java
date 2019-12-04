package com.duarte.androidproject2;

import java.io.Serializable;
import java.util.HashMap;

public class Classes implements Serializable {
    private String className;
    private String sectionNumber;
    private String classLocation;
    private String classDay;
    private Boolean notification;

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

    Classes(HashMap<String, Object> objClasses){
        try {
            if (objClasses.get("className") != null) {
                this.className = objClasses.get("className").toString();
            } else {
                this.className = "";
            }

            if (objClasses.get("sectionNumber") != null) {
                this.sectionNumber = objClasses.get("sectionNumber").toString();
            } else {
                this.sectionNumber = "";
            }

            if (objClasses.get("classLocation") != null) {
                this.classLocation = objClasses.get("classLocation").toString();
            } else {
                this.classLocation = "";
            }
            if (objClasses.get("classDay") != null) {
                this.classDay = objClasses.get("classDay").toString();
            } else {
                this.classDay = "";
            }
            if (objClasses.get("notification") != null) {
                this.notification = Boolean.parseBoolean(objClasses.get("notification").toString());
            } else {
                this.notification = false;
            }
        }catch (NullPointerException e){
            this.className = "";
            this.sectionNumber = "";
            this.classLocation = "";
            this.classDay = "";
            this.notification = false;
            e.printStackTrace();
        }
    }

    public String getClassName() { return className; }

    public String getSectionNumber() { return sectionNumber; }

    public String getClassLocation() { return classLocation; }

    public Boolean getNotification() { return notification; }

    public String getClassDay() { return classDay; }

    @Override
    public boolean equals(Object other){
        if(other == null){
            return false;
        }

        if(this.getClass() != other.getClass()){
            return false;
        }

        if( (!this.className.equals(((Classes) other).className)) ||
                (!this.sectionNumber.equals( ((Classes) other).sectionNumber )) ||
                (!this.classLocation.equals( ((Classes) other).classLocation )) ||
                (!this.classDay.equals( ((Classes) other ).classDay )) ||
                (!this.notification.equals( ((Classes) other).notification))
        ){
            return false;
        }

        return true;
    }

}
