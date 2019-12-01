package com.duarte.androidproject2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.fragment.app.DialogFragment;

//Creates the dialog box, and add listeners to the "ok" and "cancel" button
public class CreateClassDialog extends DialogFragment implements DialogInterface.OnClickListener {
    private EditText edtClassName;
    private EditText edtSectionName;
    private EditText edtClassLocation;
    private EditText edtClassDay;

    private LinearLayout layout;
    private CreateDialogInterface cdInterface;
    private String strClassName;
    private String strSectionName;
    private String strClassLocation;
    private String strClassDay;


    public CreateClassDialog(CreateDialogInterface cdInterface){
        this.cdInterface = cdInterface;
    }

    //Used to create the Dialog box
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        edtClassName = new EditText(getActivity());
        edtSectionName = new EditText(getActivity());
        edtClassName.setInputType(InputType.TYPE_CLASS_TEXT);
        edtSectionName.setInputType(InputType.TYPE_CLASS_TEXT);
        edtClassName.setHint("Class Name");
        edtSectionName.setHint("Section Name");
        edtSectionName.setHintTextColor(Color.BLACK);
        edtClassName.setHintTextColor(Color.BLACK);
        edtSectionName.setTextColor(Color.BLACK);
        edtClassName.setTextColor(Color.BLACK);

        edtClassLocation = new EditText(getActivity());
        edtClassDay = new EditText(getActivity());
        edtClassLocation.setInputType(InputType.TYPE_CLASS_TEXT);
        edtClassDay.setInputType(InputType.TYPE_CLASS_TEXT);
        edtClassLocation.setHint("Class Location");
        edtClassDay.setHint("Class Day of week");
        edtClassDay.setHintTextColor(Color.BLACK);
        edtClassLocation.setHintTextColor(Color.BLACK);
        edtClassDay.setTextColor(Color.BLACK);
        edtClassLocation.setTextColor(Color.BLACK);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
                AlertDialog.THEME_HOLO_LIGHT);

        builder.setTitle("Add a Class");
        builder.setPositiveButton("Create", this);
        builder.setNegativeButton("Cancel", null);

        layout.addView(edtClassName);
        layout.addView(edtSectionName);
        layout.addView(edtClassDay);
        layout.addView(edtClassLocation);
        builder.setView(layout);

        return builder.create();
    }
    //When dialog "Ok" button is clicked
    @Override
    public void onClick(DialogInterface dialog, int which){
        if(Global.debug){
            strClassName = "COMP101";
            strSectionName = "202";
            strClassDay = "MWF";
            strClassLocation = "NC Southwick 407";
        }else {
            strClassName = edtClassName.getText().toString();
            strSectionName = edtSectionName.getText().toString();
            strClassDay = edtClassDay.getText().toString();
            strClassLocation = edtClassLocation.getText().toString();
        }
        //check user didn't leave anything empty
        if(strClassName.isEmpty() || strSectionName.isEmpty() || strClassLocation.isEmpty() ||
        strClassDay.isEmpty()){
            Log.println(Log.DEBUG, "log", "Fields left empty");
            //TODO: add dialog box saying class name or class section is empty
            return;
        }

        dialog.dismiss();

        //Call interface function that will be i,
        cdInterface.okButtonClicked(strClassName, strSectionName, strClassDay, strClassLocation);
    }

}

