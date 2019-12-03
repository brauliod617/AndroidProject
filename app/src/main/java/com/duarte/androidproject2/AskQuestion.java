package com.duarte.androidproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AskQuestion extends AppCompatActivity implements QuestionInterface{

    FirebaseHelper firebaseHelper;

    Questions questions;

    EditText edtQuestionTitle;
    EditText edtQuestion;

    String strQuestionTitle;
    String strQuestion;

    ImageButton btnBack;

    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        //will have ["selectedClass", "email", "classOfQuestion"]
        // loaded from QuestionsPage.java from HomePage.java
        Intent intent = this.getIntent();
        bundle = intent.getExtras();

        firebaseHelper = new FirebaseHelper();

        edtQuestionTitle = findViewById(R.id.edtQuestionTitle);
        edtQuestion = findViewById(R.id.edtQuestion);

        btnBack = findViewById(R.id.btn_back_to_forum);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    public void postQuestion(View view){

        strQuestionTitle = edtQuestionTitle.getText().toString();
        strQuestion = edtQuestion.getText().toString();

        if(strQuestionTitle.isEmpty()) {
            Toast.makeText(this, "Must enter a question Title",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if(strQuestion.isEmpty()) {
            Toast.makeText(this, "Must enter a question",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if(bundle.get("selectedClass").toString().isEmpty() ||
                bundle.get("email").toString().isEmpty() ||
                bundle.get("classOfQuestion").toString().isEmpty()){
            Log.println(Log.DEBUG, "Log",
                    "Error has occurred in reading bundle from AskQuestion.java EXITING");
            return;
        }

        questions = new Questions(bundle.get("email").toString(), strQuestionTitle, strQuestion,
                bundle.get("classOfQuestion").toString());


        firebaseHelper.uploadQuestion(this, questions);

    }

    @Override //called from FirebaseHelper if upload question success
    public void questionPostedSuccessfully(){
        Toast.makeText(this, "Question posted", Toast.LENGTH_SHORT).show();
        finish();
    }
    @Override //called from FirebaseHelper if upload question fails
    public void questionPostedFailure(){
        Toast.makeText(this, "Error has occurred question was not posted",
                Toast.LENGTH_SHORT).show();
        finish();
    }

}
