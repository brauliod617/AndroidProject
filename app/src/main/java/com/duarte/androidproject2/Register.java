package com.duarte.androidproject2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Register extends AppCompatActivity {

    TextView name;
    TextView email;
    TextView password;
    TextView confirmPassword;
    Button registerButton;

    Student student;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                processRegistration();
            }
        });
    }


    public void processRegistration(){

//       TODO: Validate password match confirm password.
//             Validate username and or email does not already exist,
//             This will depend on what we choose to use for our credentials.
//             Validate the input make sure it is what we expect.
        name = findViewById(R.id.register_user);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        confirmPassword = findViewById(R.id.register_password_confirm);


        student = new Student(name.getText().toString() , email.getText().toString(), password.getText().toString());

        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.createStudentRegistration(student);
    }
}

