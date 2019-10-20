package com.duarte.androidproject2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Register extends AppCompatActivity {

    TextView name = findViewById(R.id.register_user);
    TextView email = findViewById(R.id.register_email);
    TextView password = findViewById(R.id.register_password);
    TextView confirmPassword = findViewById(R.id.register_password_confirm);
    Button registerButton = findViewById(R.id.register_button);

    Student student;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                processRegistration();
            }
        });

    }




    public void processRegistration(){

//       TODO: Validate password match confirm password
//       TODO: Validate username and or email does not already exist...
//             This will depend on what we choose to use for our credentials
        student = new Student(name.toString(), email.toString(), password.toString());

        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.createStudentRegistration(student);
    }


}

