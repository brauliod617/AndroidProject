package com.duarte.androidproject2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;


public class HomePage extends AppCompatActivity implements CreateDialogInterface{
    Student student;
    ClassesAdpter adpter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

         //get student object sent over from login or register page
         student = (Student) getIntent().getSerializableExtra("objStudent");
         adpter = attachAdapterToList();
    }

    //When user clicks on "Create class button"
    public void openDialog(View view) {
        //https://stackoverflow.com/questions/30777753/how-to-get-the-data-from-dialogfragment-to-mainactivity-in-android

        if(Global.debug){
            Log.println(Log.DEBUG, "log", "Add class button pressed");
            populateClassesListView(adpter);
        }else {
            DialogFragment newFragment = new CreateClassDialog(this);//"this" might be wrong
            newFragment.show(getSupportFragmentManager(), "Create Class");
        }

    }

    //Used to attach adapter to listview
    public ClassesAdpter attachAdapterToList(){
        ArrayList<Classes> classesArrayList = new ArrayList<>();

        ClassesAdpter adpter = new ClassesAdpter(this, classesArrayList);

        ListView listView = (ListView) findViewById(R.id.classes_list_view);
        listView.setAdapter(adpter);
        return adpter;
    }
    //for debug use only, make sure adapter is working
    public void populateClassesListView(ClassesAdpter adpter){
        List<String> daysOfClass = new ArrayList<>();
        daysOfClass.add("Mon");
        daysOfClass.add("Wed");
        daysOfClass.add("Fri");


        Classes classes = new Classes("COMP101", "202", "NC 407",
                daysOfClass, "2:00-3:00", false);

        adpter.add(classes);
    }

    @Override
    public void okButtonClicked(String strClassName, String strSectionName){
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());

        //if user left a field empty
        if(strClassName.isEmpty() || strSectionName.isEmpty()){
            Log.println(Log.DEBUG, "log", "Class name and or Class section is empty");
            //TODO: add dialog box saying class name or class section is empty
            return;
        }

        //if same student tries to sign up for same class and section, don't allow it
        if(dbHelper.isInClassExist(student.getEmail(), strClassName, strSectionName)){
            Log.println(Log.DEBUG, "log", "isInClass entry already exist");
            //TODO: show dialog saying this class and section already exist
            return;
        }

        Classes classes = new Classes(strClassName, strSectionName);
        IsInClass isInClass = new IsInClass(student.getEmail(), strClassName, strSectionName);

        //if class does not exist, create class
        if(!dbHelper.classExist(strClassName, strSectionName)){
            dbHelper.createClass(classes);
        }
        //insert into isInClass a row saying this student is in this class
        dbHelper.createIsInClass(isInClass);
        Log.println(Log.DEBUG, "log", "Class created");
        //TODO: show dialog saying class was successfully created
    }

}