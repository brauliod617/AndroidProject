package com.duarte.androidproject2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

//Creates the dialog box, and add listeners to the "ok" and "cancel" button
public class CreateClassDialog extends DialogFragment {
    TextView txvClassName;
    TextView txvSecionName;
    String strClassName;
    String strSectionName;
    DatabaseHelper dbHelper;
    IsInClass isInClass;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //dbHelper = new DatabaseHelper(getApplicationContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.add_class_custom_dialog, null))
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    //When users clicks okay
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        txvClassName = findViewById(R.id.acdClassNameTextEdit);
                        txvSecionName = findViewById(R.id.acdClassSectionTextEdit);
                        strClassName = txvClassName.getText().toString();
                        strSectionName = txvSecionName.getText().toString();
                        if(strClassName.isEmpty() || strSectionName.isEmpty()){
                            Log.println(Log.DEBUG, "log", "Class name and or Class section is empty");
                            //TODO: add dialog box saying class name or class section is empty
                            return;
                        }
                        classes = new Classes(strClassName, strSectionName);
                        addClass();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    //When user clicks cancel
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                });
        builder.setTitle("CREATE CLASS");
        return builder.create();
    }
    public void addClass(){
        if(dbHelper.isInClassExist(student.getEmail(), strClassName, strSectionName)){
            Log.println(Log.DEBUG, "log", "isInClass entry already exist");
            //TODO: show dialog saying this class and section already exist
            return;
        }

        isInClass = new IsInClass(student.getEmail(), strClassName, strSectionName);

        //if class does not exist, create class
        if(!dbHelper.classExist(strClassName, strSectionName)){
            dbHelper.createClass(classes);
        }
        //insert into isInClass a row saying this student is in this class
        dbHelper.createIsInClass(isInClass);
        Log.println(Log.DEBUG, "log", "Class created");
    }
}