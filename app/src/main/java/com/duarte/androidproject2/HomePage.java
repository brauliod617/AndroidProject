package com.duarte.androidproject2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomePage extends AppCompatActivity implements CreateDialogInterface, FirebaseClassesInterface{
    Student student;
    ClassesAdpter adpter;
    FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

         //get student object sent over from login or register page
         student = (Student) getIntent().getSerializableExtra("objStudent");
         adpter = attachAdapterToList();
         firebaseHelper = new FirebaseHelper();
         //load all current class and populate into class list
    }

    //When user clicks on "Create class button"
    public void openDialog(View view) {
        //https://stackoverflow.com/questions/30777753/how-to-get-the-data-from-dialogfragment-to-mainactivity-in-android

        if(false){
            Log.println(Log.DEBUG, "log", "Add class button pressed");
            populateClassesListViewDebug(adpter);
        }else {
            DialogFragment newFragment = new CreateClassDialog(this);//"this" might be wrong
            newFragment.show(getSupportFragmentManager(), "Create Class");
        }

    }

    //Used to attach adapter to listview
    public ClassesAdpter attachAdapterToList(){
        ArrayList<Classes> classesArrayList = new ArrayList<>();

        ClassesAdpter adpter = new ClassesAdpter(this, classesArrayList);

        ListView listView = findViewById(R.id.classes_list_view);
        listView.setAdapter(adpter);
        return adpter;
    }
    //for debug use only, make sure adapter is working
    public void populateClassesListViewDebug(ClassesAdpter adpter){

        Classes classes = new Classes("COMP101", "202", "NC 407",
                "MWF", false);

        adpter.add(classes);
    }


    @Override //Implementing CreateDialogInterface's okButtonClicked function
    public void okButtonClicked(String strClassName, String strSectionName, String classDay,
                                String classLocation){

        //if same student tries to sign up for same class and section, don't allow it
//        if(dbHelper.isInClassExist(student.getEmail(), strClassName, strSectionName)){
//            Log.println(Log.DEBUG, "log", "isInClass entry already exist");
//            //TODO: show dialog saying this class and section already exist
//            return;
//        }

        Classes classes = new Classes(strClassName, strSectionName);
        IsInClass isInClass = new IsInClass(student.getEmail(), strClassName, strSectionName);

        //get the classes => will call either onGetClassesSuccess or onGetClassesFailure vv
        firebaseHelper.getClasses(this, classes);

//        //insert into isInClass a row saying this student is in this class
//        dbHelper.createIsInClass(isInClass);
//        Log.println(Log.DEBUG, "log", "Class created");
//        //TODO: show dialog saying class was successfully created
    }

    @Override //Implementing FirebaseClassesInterface OnSuccess function
    public void onGetClassesSuccess(Task<QuerySnapshot> task, Classes classes) {
        //called if we pulled classes documents successfully from firebase firestore
        boolean classExists = false;

        //validate if current class exist
        //TODO: handle possible exception from task
        for(QueryDocumentSnapshot current : task.getResult()) {
            if(current.get("className").equals(classes.getClassName())
                    && current.get("sectionNumber").equals(classes.getSectionNumber())) {
                classExists = true;
            }
        }

        //if class does not exist, create class
        if(!classExists) {
            firebaseHelper.registerClass(classes);
        }
    }

    @Override  //Implementing FirebaseClassesInterface OnFailure function
    public void onGetClassesFailed(Task<QuerySnapshot> task) {

    }
}