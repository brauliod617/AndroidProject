package com.duarte.androidproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Register extends AppCompatActivity {

    TextView name;
    TextView email;
    TextView password;
    TextView confirmPassword;
    Button registerButton;

    Student student;
    FirebaseHelper firebaseHelper;


    // Initialize Firebase objects
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.register_user);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        confirmPassword = findViewById(R.id.register_password_confirm);

        registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                processRegistration();
            }
        });
    }

    public void processRegistration(){

        //TODO: Validate password match confirm password.
        //      Validate username and or email does not already exist,
        //      This will depend on what we choose to use for our credentials.
        //       Validate the input make sure it is what we expect.
<<<<<<< HEAD
        Log.println(Log.DEBUG, "Log", "Created Registration" + email);
        if (!validateForm()) {
            return;
        }

=======
        name = findViewById(R.id.register_user);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        confirmPassword = findViewById(R.id.register_password_confirm);

        student = new Student(name.getText().toString() , email.getText().toString(), password.getText().toString());
>>>>>>> master

        //TODO: Validate student registration does not already exist before creating a new one
        //      Throwing an unhandled expressin right now
        //      android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: registration.email (code 1555)
        //databaseHelper = new DatabaseHelper(getApplicationContext());
        //databaseHelper.createStudentRegistration(student);

        firebaseHelper = new FirebaseHelper();

        createAccount(email.getText().toString(), password.getText().toString());

        firebaseHelper.registerStudentFirebase(student);
        Log.println(Log.DEBUG, "Log", "Created Registration");
        super.onBackPressed();
    }

    // Create a new account
    private void createAccount(String email, String password) {

        // Create user with email and password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(String.valueOf(Log.DEBUG), "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user.
                        // If sign in succeeds the auth state listener will be notified
                        // and logic to handle the signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this,
                                    "Welcome " + mAuth.getCurrentUser().getEmail(),
                                    Toast.LENGTH_SHORT).show();
                            // Create new user
                            firebaseHelper.registerStudentFirebase(student);
                        } else {
                            Toast.makeText(Register.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private boolean validateForm() {
        boolean isValid = true;

        // Validate Name, Email, and Password
        String sName = name.getText().toString();
        if (TextUtils.isEmpty(sName)) {
            name.setError(getString(R.string.error_field_required));
            isValid = false;
        } else {
            name.setError(null);
        }

        String sEmail = email.getText().toString();
        if (TextUtils.isEmpty(sEmail)) {
            email.setError(getString(R.string.error_field_required));
            isValid = false;
        } else {
            email.setError(null);
        }

        String sPassword = password.getText().toString();
        if (TextUtils.isEmpty(sPassword)) {
            password.setError(getString(R.string.error_field_required));
            isValid = false;
        } else {
            password.setError(null);
        }

        return isValid;
    }


}


