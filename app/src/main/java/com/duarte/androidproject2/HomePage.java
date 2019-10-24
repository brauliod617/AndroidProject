package com.duarte.androidproject2;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;


public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
    }

    public void openDialog(View view) {
        DialogFragment newFragment = new CreateClassDialog();
        newFragment.show(getSupportFragmentManager(), "Create Class");

    }

}
