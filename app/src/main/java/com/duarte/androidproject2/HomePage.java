package com.duarte.androidproject2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;


public class HomePage extends AppCompatActivity {
    Student student;
    Classes classes;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

         //get student object sent over from login or register page
         student = (Student) getIntent().getSerializableExtra("student");
    }

    //When user clicks on "Create class button"
    public void openDialog(View view) {
        DialogFragment newFragment = new CreateClassDialog();
        newFragment.show(getSupportFragmentManager(), "Create Class");

    }


}