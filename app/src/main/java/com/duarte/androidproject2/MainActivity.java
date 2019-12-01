package com.duarte.androidproject2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements FirebaseInterface{

//  TODO: Add forget password feature

    TextView txvEmail;
    TextView txvPassword;
    String strEmail;
    String strPassword;
    Student student;

    //DatabaseHelper dbHelper;
    FirebaseHelper firebaseHelper;
    FirebaseInterface firebaseInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //Respond to Onclick from "create an account"
    public void register(View view){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);

        //TODO: Make it so it starts Homepage with registerd student class,
        //      See login(View view) for example
    }

    //Respond to OnClick from "Login"
    public void login(View view) {

        if(Global.debug){
//            strEmail = "Juyeong_Seo@student.uml.edu";
//            strPassword = "tjtjtj";
              strEmail = "Test@yahoo.com";
              strPassword = "Duarte";
        }else {
            txvEmail = findViewById(R.id.main_User);
            txvPassword = findViewById(R.id.main_Password);
            strEmail = txvEmail.getText().toString();
            strPassword = txvPassword.getText().toString();
        }

        firebaseHelper = new FirebaseHelper();
        //check if user has entered a username and password
        if(strEmail.isEmpty() || strPassword.isEmpty()){
            //TODO: create dialog telling user to enter username and password
            //      if we have time change the color of missing text to red to help user find error
            Log.println(Log.DEBUG, "Log","missing password or username");
            return;
        }

        firebaseHelper.validateStudentLogin(strEmail, strPassword, this);

    }//end of login(View view)


    @Override
    public void onSuccess(Task<QuerySnapshot> task){
        for (QueryDocumentSnapshot document : task.getResult()) {
            HashMap<String, Object> current = (HashMap<String, Object>) document.getData();
            if(current.get("email").equals(strEmail)){
                if(current.get("password").equals(strPassword)) {
                    student = new Student(current);
                    Log.println(Log.DEBUG, "log", "Login successful");
                    Intent intent = new Intent(this, HomePage.class);
                    Bundle bundle = new Bundle();

                    //We pass student obj to following activity so we know which user is logged in
                    bundle.putSerializable("objStudent", student);
                    intent.putExtras(bundle);

                    //start HomePage activity
                    startActivity(intent);

                }else {
                    Log.println(Log.DEBUG, "Log","Login was not successful");
                    return;
                }
                break;
            }
        }
    }

    @Override
    public void onFailed(Task<QuerySnapshot> data) {
        Log.println(Log.DEBUG, "Log","ERROR has occurred getting student");
    }





//    public boolean validateLogin(String email, String password, FirebaseHelper firebaseHelper){
//
//        //TODO: Validate email format,
//
//
//        //if student registration does not exist
//        if(!dbHelper.studentExist(email)){
//            return false;
//        }
//
//        //if user entered the correct password
//        if(dbHelper.validatePassword(email, password)) {
//            return true;
//        }
//
//        return false;
//    }

}

