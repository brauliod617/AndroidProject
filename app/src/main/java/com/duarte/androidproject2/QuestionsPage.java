package com.duarte.androidproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuestionsPage extends AppCompatActivity {

    QuestionsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_page);


        adapter = attachAdapterToList();
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

        //will have ["selectedClass", "email"] loaded from HomePage.java
        Bundle bundle = getIntent().getExtras();

        intent.putExtras(bundle);

        startActivity(intent);
    }


}
