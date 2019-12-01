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
    private LinearLayout layout;
    private CreateDialogInterface cdInterface;

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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);

        builder.setTitle("Add a Class");
        builder.setPositiveButton("Create", this);
        builder.setNegativeButton("Cancel", null);

        layout.addView(edtClassName);
        layout.addView(edtSectionName);
        builder.setView(layout);

        return builder.create();
    }
    //When dialog "Ok" button is clicked
    @Override
    public void onClick(DialogInterface dialog, int which){
        String className;
        String sectionName;
        if(Global.debug){
            className = "COMP101";
            sectionName = "202";
        }else {
            className = edtClassName.getText().toString();
            sectionName = edtSectionName.getText().toString();
        }
        //check user didn't leave anything empty
        if(className.isEmpty() || sectionName.isEmpty()){
            Log.println(Log.DEBUG, "log", "Class name and or Class section is empty");
            //TODO: add dialog box saying class name or class section is empty
            return;
        }

        dialog.dismiss();
        //Call interface function that will be i,
        cdInterface.okButtonClicked(className, sectionName);
    }
}

