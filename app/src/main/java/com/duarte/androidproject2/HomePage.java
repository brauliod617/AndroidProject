package com.duarte.androidproject2;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomePage extends AppCompatActivity implements CreateDialogInterface, FirebaseClassesInterface{
    Student student;
    ClassesAdpter adpter;
    FirebaseHelper firebaseHelper;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

         //get student object sent over from login or register page
         student = (Student) getIntent().getSerializableExtra("objStudent");
         adpter = attachAdapterToList();
         firebaseHelper = new FirebaseHelper();
         //load all current class and populate into class list

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initNavigationDrawer();
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

        Classes classes = new Classes(strClassName, strSectionName, classLocation, classDay, false);
        IsInClass isInClass = new IsInClass(student.getEmail(), strClassName, strSectionName);

        //get the classes => will call either onGetClassesSuccess or onGetClassesFailure vv
        //will create class tuple if it does not already exist
        firebaseHelper.getClasses(this, classes);

        //will add isInClass tuple if it does not already exist
        firebaseHelper.getisInClasses(this, isInClass);

        Log.println(Log.DEBUG, "log", "Class created");
        //TODO: show dialog saying class was successfully created
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
        //if class exists all we want to do is add an entry in the isInClass table
    }

    @Override  //Implementing FirebaseClassesInterface OnFailure function
    public void onGetClassesFailed(Task<QuerySnapshot> task) {

    }

    @Override //Implementing FirebaseClassesInterface function
    public void onGetIsInClassesSuccess(Task<QuerySnapshot> task, IsInClass isInClass){
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
            firebaseHelper.registerIsInClass(isInClass);
        }
    }

    @Override //Implementing FirebaseClassesInterface function
    public void onGetIsInClassesFailed(Task<QuerySnapshot> task){

    }


    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){
                    case R.id.home:
                        Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.settings:
                        Toast.makeText(getApplicationContext(),"Settings",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.notification:
                        Toast.makeText(getApplicationContext(),"Notification",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        finish();

                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView tv_email = (TextView)header.findViewById(R.id.tv_email);
        //TODO: need to change header with the user id/email
        tv_email.setText("Juyeong_Seo@student.uml.edu");
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}

