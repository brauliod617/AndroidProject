package com.duarte.androidproject2;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionsPage extends AppCompatActivity implements LoadQuestionInteface{

    QuestionsAdapter adapter;
    List<Questions> questionsList;
    String className;
    FirebaseHelper firebaseHelper;
    Bundle bundle;
    TextView txvClassTitleForNavBar;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_page);

        questionsList = new ArrayList<>();
        firebaseHelper = new FirebaseHelper();

        adapter = attachAdapterToList();
        adapter.setLoadQuestionInteface(this);

        //will have ["selectedClass", "email", "classOfQuestion"]
        //loaded from HomePage.java to be used in PostReplyActivity
        bundle = getIntent().getExtras();

        className = getIntent().getExtras().get("classOfQuestion").toString();
        firebaseHelper.downloadQuestions(this);

        txvClassTitleForNavBar = findViewById(R.id.textViewnav5);

        //TODO: validate get("classOfQuestion) is not null and not bigger then like 10 chars or
        //      something reasonable
        txvClassTitleForNavBar.setText(getIntent().getExtras().get("classOfQuestion").toString());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initNavigationDrawer();
    }

    public QuestionsAdapter attachAdapterToList(){
        ArrayList<Questions> questionsArrayList = new ArrayList<>();

        QuestionsAdapter adapter = new QuestionsAdapter(this, questionsArrayList);

        ListView listView = findViewById(R.id.questionsPageListView);
        listView.setAdapter(adapter);


        return adapter;

    }

    public void addClassesButtonListener(View view){
        Intent intent = new Intent(this, AskQuestion.class);

        //will have ["selectedClass", "email", "classOfQuestion"] loaded from HomePage.java
        Bundle bundle = getIntent().getExtras();

        intent.putExtras(bundle);

        startActivity(intent);
    }


    @Override
    public void questionDownloadSuccessfull(Task<QuerySnapshot> task){

        for(QueryDocumentSnapshot current : task.getResult()){
            //TODO: this is risky need to add validation here make sure current is what we expect
            //TODO: maybe find more effecient way of doing this, have to go through all questions rn

            //if current question belongs to this class
            if(current.get("classOfQuestion").toString().equals(className)){
                Questions temp = new Questions( (HashMap<String, Object>) current.getData());
                adapter.add(temp);
            }
        }

    }

    @Override
    public void questionDownloadFailed(Exception e){
        e.printStackTrace();
    }

    //when user clicks on a question
    @Override
    public void onQuestionClicked(Questions currentQuestion){
        Intent intent = new Intent(this, ReplyActivity.class);

        //bundle came from Homepage with
        //["selectedClass", "email", "classOfQuestion"]
        //adding "question" now

        bundle.putSerializable("question", currentQuestion);

        intent.putExtras(bundle);

        startActivity(intent);
        //start reply activity with current question
    }


    /**************************************Navigation**************************************************/
    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){
                    case R.id.home:
                        Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawers();
                        home();
                        //finish();
                        break;
                    /*case R.id.notification:
                        Toast.makeText(getApplicationContext(),"Notification",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                        */
                    case R.id.reset_password:
                        Toast.makeText(getApplicationContext(),"Reset Password",Toast.LENGTH_LONG).show();
                        resetPassword();
                        //finish();
                        break;
                    case R.id.logout:
                        logOut();
                        finish();
                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView tv_email = (TextView)header.findViewById(R.id.tv_email);
        // change header with the user id/email
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        tv_email.setText(email);
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

    private void logOut() {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void resetPassword() {
        Intent intent = new Intent(getApplicationContext(),ResetActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void home() {
        Intent intent = new Intent(getApplicationContext(),HomePage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

/********************************End of Navigation*************************************************/
}
