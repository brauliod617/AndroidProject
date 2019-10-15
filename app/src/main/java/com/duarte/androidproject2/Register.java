package com.duarte.androidproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Register extends AppCompatActivity {

    TextView name;
    TextView email;
    TextView password;
    TextView confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
    }



    public void ProcessRegistration(View view){

       name = (TextView) findViewById(R.id.register_user);
       email = (TextView) findViewById(R.id.register_email);
       password = (TextView) findViewById(R.id.register_password);
       confirmPassword = (TextView) findViewById(R.id.register_password_confirm);


    }

    public final class RegistrationSchema {

        private RegistrationSchema() {}

        public class FeedEntry implements BaseColumns {
            public static final String TABLE_NAME = "registration";
            public static final String COLUMN_NAME_USERNAME = "userName";
            public static final String COLUMN_NAME_EMAIL = "email";
            /*TODO: Research on what is secure way to store passwords as opposed to plain text. Maybe something like aws secure store*/
            public static final String COLUMN_NAME_PASSWORD = "password";
        }
    }


}

