package com.duarte.androidproject2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

//  TODO: Add forget password feature

    TextView txvUserName;
    TextView txvPassword;
    Student student;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //Respond to Onclick from "create an account"
    public void register(View view){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
    //Respond to OnClick from "Login"
    public void login(View view) {
        String strUsername;
        String strPassword;

        txvUserName = findViewById(R.id.main_User);
        txvPassword = findViewById(R.id.main_Password);
        strUsername = txvUserName.getText().toString();
        strPassword = txvPassword.getText().toString();
        dbHelper = new DatabaseHelper(getApplicationContext());

        //check if user has entered a username and password
        if(strUsername.isEmpty() || strPassword.isEmpty()){
            //TODO: create dialog telling user to enter username and password
            //      if we have time change the color of missing text to red to help user find error
            Log.println(Log.DEBUG, "Log","missing password or username");
            return;
        }

        if(!validateLogin(strUsername, strPassword, dbHelper)){
            //TODO: create dialog telling user password or username is wrong
            Log.println(Log.DEBUG, "Log","Invalid password or username");
            return;
        }

        //At this point we know we have a registered username with a correct password, we create a
        //student obj with data pulled from db. This object will represent the logged in student
        student =  dbHelper.getStudentRegistration(strUsername);

        Intent intent = new Intent(this, HomePage.class);
        Bundle bundle = new Bundle();


        //We pass student obj to following activity so we know which user is logged in
        bundle.putSerializable("objStudent", student);
        intent.putExtras(bundle);

        Log.println(Log.DEBUG, "log", "Login successful");

        startActivity(intent);
    }

    public boolean validateLogin(String email, String password, DatabaseHelper dbHelper){

        //TODO: Validate email format

        //if student registration does not exist
        if(!dbHelper.studentExist(email)){
            return false;
        }

        //if user entered the correct password
        if(dbHelper.validatePassword(email, password)) {
            return true;
        }

        return false;
    }

}

