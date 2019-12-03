package com.duarte.androidproject2;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReplyActivity extends AppCompatActivity {

    Questions question;

    TextView txvClassName;
    TextView txvQuestionTitle;
    TextView txvOpName;
    TextView txvQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        Bundle bundle = getIntent().getExtras();

        if(bundle.get("question") != null){
            question = (Questions) bundle.get("question");
        }else {
            //Fatal error
            Toast.makeText(this, "Fatal Error has Occurred", Toast.LENGTH_SHORT).show();
            finish();
        }

        txvClassName = findViewById(R.id.txvClassNameReplyAct);
        txvQuestionTitle = findViewById(R.id.txvQuestionTitle);
        txvOpName = findViewById((R.id.txvOPname));
        txvQuestion = findViewById(R.id.txvQuestion);

        txvClassName.setText(question.getClassOfQuestion());
        txvQuestionTitle.setText(question.getQuestionTitle());
        txvOpName.setText(question.getOpEmail());
        txvQuestion.setText(question.getContent());
    }


}
