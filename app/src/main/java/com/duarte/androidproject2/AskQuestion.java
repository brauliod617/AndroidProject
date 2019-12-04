package com.duarte.androidproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

    private FirebaseHelper firebaseHelper;
    Questions questions;
    TextView tv_class_title;
    private EditText edtQuestionTitle;
    private EditText edtQuestion;
    String strQuestionTitle;
    String strQuestion;
    ImageButton btnBack;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        //will have ["selectedClass", "email", "classOfQuestion"]
        // loaded from QuestionsPage.java from HomePage.java
        Intent intent = this.getIntent();
        bundle = intent.getExtras();

        firebaseHelper = new FirebaseHelper();

        tv_class_title = findViewById(R.id.tx_class_name_ask_question);

        if(bundle.get("classOfQuestion") != null) {
            tv_class_title.setText(bundle.get("classOfQuestion").toString());
        }

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
                    Toast.LENGTH_LONG).show();
            return;
        }

        if(strQuestion.isEmpty()) {
            Toast.makeText(this, "Must enter a question",
                    Toast.LENGTH_LONG).show();
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
        Toast.makeText(this, "Question posted", Toast.LENGTH_LONG).show();
        finish();
    }
    @Override //called from FirebaseHelper if upload question fails
    public void questionPostedFailure(){
        Toast.makeText(this, "Error has occurred question was not posted",
                Toast.LENGTH_LONG).show();
        finish();
    }

}
