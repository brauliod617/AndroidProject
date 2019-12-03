package com.duarte.androidproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionsPage extends AppCompatActivity implements LoadQuestionInteface{

    QuestionsAdapter adapter;
    List<Questions> questionsList;
    String className;
    FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_page);

        questionsList = new ArrayList<>();
        firebaseHelper = new FirebaseHelper();

        adapter = attachAdapterToList();

        className = getIntent().getExtras().get("classOfQuestion").toString();
        firebaseHelper.downloadQuestions(this);
    }

    public QuestionsAdapter attachAdapterToList(){
        ArrayList<Questions> questionsArrayList = new ArrayList<>();

        QuestionsAdapter adapter = new QuestionsAdapter(this, questionsArrayList);

        ListView listView = findViewById(R.id.questionsPageListView);
        listView.setAdapter(adapter);

        return adapter;

    }

    public void addClassesButtonListener(View view){
        Intent intent = new Intent(this, AskQuestion.class);

        //will have ["selectedClass", "email", "classOfQuestion"] loaded from HomePage.java
        Bundle bundle = getIntent().getExtras();

        intent.putExtras(bundle);

        startActivity(intent);
    }


    @Override
    public void questionDownloadSuccessfull(Task<QuerySnapshot> task){

        for(QueryDocumentSnapshot current : task.getResult()){
            //TODO: this is risky need to add validation here make sure current is what we expect
            //TODO: maybe find more effecient way of doing this, have to go through all questions rn

            //if current question belongs to this class
            if(current.get("classOfQuestion").toString().equals(className)){
                Questions temp = new Questions( (HashMap<String, Object>) current.getData());
                adapter.add(temp);
            }
        }

    }

    @Override
    public void questionDownloadFailed(Exception e){
        e.printStackTrace();
    }


}
