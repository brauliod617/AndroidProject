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
import java.util.HashMap;
import java.util.List;


public class HomePage extends AppCompatActivity implements CreateDialogInterface,
        FirebaseClassesInterface{
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
        loadClasses();
    }
/**************************************Add Class***************************************************/

    //When user clicks on "Create class button", will bring up addclass dialog wait
    //wait for user to press ok or cancel, if user press dialog closes and nothing happens
    //if user presses ok the next function is called
    public void openDialog(View view) {
    //https://stackoverflow.com/questions/30777753/how-to-get-the-data-from-dialogfragment-to-mainactivity-in-android

        if(false){
            Log.println(Log.DEBUG, "log", "Add class button pressed");
            Classes debugclasses = new Classes("DebugClass", "101",
                    "debug hall", "MWF", false);
            populateClassesListViewDebug(adpter, debugclasses);
        }else {
            DialogFragment newFragment = new CreateClassDialog(this);
            newFragment.show(getSupportFragmentManager(), "Create Class");
        }
    }

    //called when user presses okay on add class dialog, will iniate firebase processes
    //to create class db tuple if it doesnt exist, and create isInClass tuple if it does not exist,
    //add class to students classList
    @Override //Implementing CreateDialogInterface's okButtonClicked function
    public void okButtonClicked(String strClassName, String strSectionName, String classDay,
                                String classLocation){

        Classes classes = new Classes(strClassName, strSectionName, classLocation, classDay,
                false);
        IsInClass isInClass = new IsInClass(student.getEmail(), strClassName, strSectionName);

        //get the classes => will call either onGetClassesSuccess or onGetClassesFailure vv
        //will create class tuple if it does not already exist
        firebaseHelper.getClasses(this, classes);

        //will add isInClass tuple if it does not already exist
        firebaseHelper.getisInClasses(this, isInClass, classes);

        Log.println(Log.DEBUG, "log", "Class created");
        //TODO: show dialog saying class was successfully created
    }

    //called when getting classes from database is successful
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
        //if class exists all we want to do is add an entry in the isInClass table
    }

    //called when getting isInClasses table from database is successful
    @Override //Implementing FirebaseClassesInterface function
    public void onGetIsInClassesSuccess(Task<QuerySnapshot> task, IsInClass isInClass,
                                        Classes classes){
        //called if we pulled isInclasses documents successfully from firebase firestore
        boolean isInClassExists = false;

        //check if current isInClass tuple already exist
        for(QueryDocumentSnapshot current : task.getResult()) {
            if(current.get("email").equals(isInClass.getEmail()) &&
                    current.get("className").equals(isInClass.getClassName()) &&
                    current.get("sectionNumber").equals(isInClass.getSectionNumber())) {
                isInClassExists = true;
            }
        }

        //if same student tries to sign up for same class and section, don't allow it
        if(isInClassExists){
            Log.println(Log.DEBUG, "log", "isInClass entry already exist");
            //TODO: show dialog saying this class and section already exist
        }else {
            //if student is not registered for this class, we add the entry
            //add isInClass tuple to database
            firebaseHelper.registerIsInClass(isInClass);
            //add class to homepage
            populateClassesListViewDebug(adpter, classes);
            //add class to students classList
            student.addToClassesList(classes);

            //upload student's Classes list in firestore db
            firebaseHelper.addClassToStudentClassList(student);

        }
    }

    //for debug use only, make sure adapter is working
    public void populateClassesListViewDebug(ClassesAdpter adpter, Classes classes){
        adpter.add(classes);
    }

    //called when getting classes from database fails
    @Override  //Implementing FirebaseClassesInterface OnFailure function
    public void onGetClassesFailed(Task<QuerySnapshot> task) {
        //TODO:Figure out what to do here
    }

    @Override //Implementing FirebaseClassesInterface function
    public void onGetIsInClassesFailed(Task<QuerySnapshot> task){
        //TODO:Figure out what to do here
    }

/**************************************End Of Add Class********************************************/

/**************************************Load Classes************************************************/
    //Load all classes student is registerd for at beginning of app
    public void loadClasses(){
        firebaseHelper.loadClasses(this);
    }

    @Override //Implementing FirebaseClassesInterface function
    public void onLoadClassesSuccess(Task<QuerySnapshot> task){
        List<Classes> classesList = new ArrayList<>();

        //read database classes document, create class obj for each class in db, and add it to a list
        for(QueryDocumentSnapshot current : task.getResult()) {
            //TODO: this is risky need to add validation here make sure current is what we expect
            Classes temp = new Classes( (HashMap<String, Object>) current.getData() );

            //if current class is a class the student is registerd for
            if(student.isInClass( temp )) {
                //add it to the class list
                classesList.add(temp);
            }

        }
        //call to function to create classes in homepage from class list
        loadClassesFromList(classesList);

    }

    @Override //Implementing FirebaseClassesInterface function
    public void onLoadClassesFailed(Task<QuerySnapshot> task){
        //TODO: figure out what to do here
    }

    public void loadClassesFromList(List<Classes> classesList){
        //for each class in classList
        for(Classes classes : classesList){
            //add class to adapter
            adpter.add(classes);
        }
    }

/**************************************End Of Load Classes*****************************************/





    //Used to attach adapter to listview
    public ClassesAdpter attachAdapterToList(){
        ArrayList<Classes> classesArrayList = new ArrayList<>();

        ClassesAdpter adpter = new ClassesAdpter(this, classesArrayList);

        ListView listView = findViewById(R.id.classes_list_view);
        listView.setAdapter(adpter);
        return adpter;
    }
}