package com.duarte.androidproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;

public class ReplyActivity extends AppCompatActivity implements PullAnswersInterface{

    Questions question;

    TextView txvClassName;
    TextView txvQuestionTitle;
    TextView txvOpName;
    TextView txvQuestion;
    FirebaseHelper firebaseHelper;

    ImageButton btnBack;

    Bundle bundle;

    AnswerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        bundle = getIntent().getExtras();

        if(bundle.get("question") != null){
            question = (Questions) bundle.get("question");
        }else {
            //Fatal error
            Toast.makeText(this, "Fatal Error has Occurred",
                    Toast.LENGTH_LONG).show();
            finish();
        }

        txvClassName = findViewById(R.id.txvClassNameReplyAct);
        txvQuestionTitle = findViewById(R.id.txvQuestionTitle);
        txvOpName = findViewById((R.id.txvOPname));
        txvQuestion = findViewById(R.id.txvQuestion);
        btnBack = findViewById(R.id.btn_back_to_question);

        txvClassName.setText(question.getClassOfQuestion());
        txvQuestionTitle.setText(question.getQuestionTitle());
        txvOpName.setText(question.getOpEmail());
        txvQuestion.setText(question.getContent());

        firebaseHelper = new FirebaseHelper();

        adapter = attachAdapterToList();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


    }

    //after postAnswer is finished this activity will resume and this will be called
    //and here we will pull the answers to show newly added answer
    @Override
    protected void onResume(){
        super.onResume();
        adapter.clear();

        //will pull answers from DB, if success will call onPullAnswersSuccess
        //if failure will call onPullAnswerFailure, both implemented inside this class
        firebaseHelper.pullAnswers(this);
    }

    public void onClickAddAnswer(View view){
        Intent intent = new Intent(getApplicationContext(), PostReplyActivity.class);

        //bundle came from Questions Page with
        //["selectedClass", "email", "classOfQuestion", "question"]
        //email is active user email

        intent.putExtras(bundle);

        startActivity(intent);
    }

    public AnswerAdapter attachAdapterToList(){
        ArrayList<Answer> answerArrayList = new ArrayList<>();

        AnswerAdapter adapter = new AnswerAdapter(this, answerArrayList);

        ListView listView = findViewById(R.id.lsvAnswers);
        listView.setAdapter(adapter);
        return adapter;
    }

    //called from firebaseHelper if pulling the answers were successful
    @Override
    public void onPullAnswersSuccess(Task<QuerySnapshot> task){

        for(QueryDocumentSnapshot current : task.getResult()){
            //TODO: validate current is what we expect, see Answer.java constructor

            //does this answer belong to this question
            if(question.getQuestionTitle().equals(current.get("questionName"))){
                Answer temp = new Answer((HashMap<String, Object>) current.getData());
                adapter.add(temp);
            }
        }
    }

    @Override
    public void onPullAnswersFailed(Exception e) {
        //Fatal error
        Toast.makeText(this, "Failed to pull answers", Toast.LENGTH_LONG).show();
        finish();
        e.printStackTrace();
    }
}
