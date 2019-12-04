package com.duarte.androidproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

public class PostReplyActivity extends AppCompatActivity implements PostReplyInterface{

    TextView edtQuestionTitle;
    EditText edtAnswer;
    Questions questions;
    FirebaseHelper firebaseHelper;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_reply);

        //bundle came from Reply Activity with
        //["selectedClass", "email", "classOfQuestion", "question"]
        //email is active user email
        bundle = getIntent().getExtras();

        if(bundle.get("question") != null){
            questions = (Questions) bundle.get("question");
        }else {
            //fatal error
            Log.println(Log.DEBUG, "Log", "ERROR has occurred getting question");
            finish();
        }
        edtAnswer = findViewById(R.id.txeAnswer);
        edtQuestionTitle = findViewById(R.id.edtQuestionTitlePQ);
        edtQuestionTitle.setText(questions.getQuestionTitle());

        firebaseHelper = new FirebaseHelper();
    }

    public void onXClick(View view){
        onBackPressed();
    }

    public void onPostAnswerClick(View view) {

        String answer = edtAnswer.getText().toString();

        if(answer.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Must have an answer",Toast.LENGTH_LONG).show();
            return;
        }

        if( bundle.get("email") != null ){
            firebaseHelper.postAnswer(this, questions.getQuestionTitle(),
                    bundle.get("email").toString(), answer);

        }
    }

    @Override
    public void onAddAnswerSuccess(){
        Toast.makeText(getApplicationContext(),"Answer posted Successfully",
                Toast.LENGTH_LONG).show();

        super.finish();
    }
    @Override
    public void onAddAnswerFailed(Exception e){
        Toast.makeText(getApplicationContext(),"Error occurred when posting answer",
                Toast.LENGTH_LONG).show();
    }
}
