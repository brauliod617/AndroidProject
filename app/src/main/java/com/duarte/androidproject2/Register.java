package com.duarte.androidproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private static final String TAG = Register.class.getSimpleName();

    private TextView name;
    private TextView email;
    private TextView password;
    private TextView confirmPassword;
    private Button registerButton;

    private Student student;
    FirebaseHelper firebaseHelper;


    // Initialize Firebase objects
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mRef;

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
            public void onClick(View v) {
                processRegistration();
            }
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("user");
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(String.valueOf(Log.DEBUG), "onAuthStateChanged:signed_in:" + user.getUid());

                    // Send user to main activity
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                updateUI(user);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    public void processRegistration() {

        //TODO: Validate password match confirm password.
        //      Validate username and or email does not already exist,
        //      This will depend on what we choose to use for our credentials.
        //       Validate the input make sure it is what we expect.

        Log.d(TAG, "Create Registration" + email);

        Log.println(Log.DEBUG, "Log", "Created Registration" + email);

        if (!validateForm()) {
            return;
        }



        student = new Student(name.getText().toString(), email.getText().toString(), password.getText().toString());

        //TODO: Validate student registration does not already exist before creating a new one
        //      Throwing an unhandled expression right now
        //      android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: registration.email (code 1555)
        //databaseHelper = new DatabaseHelper(getApplicationContext());
        //databaseHelper.createStudentRegistration(student);

        //firebaseHelper = new FirebaseHelper();


        createAccount(email.getText().toString().trim(), password.getText().toString().trim());

        Log.println(Log.DEBUG, "Log", "Created Registration");
        super.onBackPressed();
    }

    // Create a new account
    private void createAccount(final String nEmail, final String nPassword) {

        // Create user with email and password
        mAuth.createUserWithEmailAndPassword(nEmail, nPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user.
                        // If sign in succeeds the auth state listener will be notified
                        // and logic to handle the signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this,
                                    "Welcome " + mAuth.getCurrentUser().getEmail(),
                                    Toast.LENGTH_SHORT).show();
                            // Create new user
                            FirebaseUser user = mAuth.getCurrentUser();
                            //firebaseHelper.registerStudentFirebase(student);
                            student = new Student(name.getText().toString(), nEmail, nPassword);
                            mRef.child(user.getUid()).setValue(student);
                        } else {
                            Toast.makeText(Register.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {

        } else {

        }
    }

    private boolean validateForm() {
        boolean isValid = true;

        // Validate Name, Email, and Password
        String sName = name.getText().toString().trim();
        if (TextUtils.isEmpty(sName)) {
            name.setError(getString(R.string.error_field_required));
            isValid = false;
        } else {
            name.setError(null);
        }

        String sEmail = email.getText().toString().trim();
        if (TextUtils.isEmpty(sEmail)) {
            email.setError(getString(R.string.error_field_required));
            isValid = false;
        } else {
            email.setError(null);
        }

        String sPassword = password.getText().toString().trim();
        if (TextUtils.isEmpty(sPassword)) {
            password.setError(getString(R.string.error_field_required));
            isValid = false;
        } else {
            password.setError(null);
        }

        String sConfirmPassword = confirmPassword.getText().toString().trim();
        if (!sConfirmPassword.equals(sPassword)) {
            confirmPassword.setError(getString(R.string.error_incorrect_password));
            isValid = false;
        } else {
            password.setError(null);
        }

        return isValid;
    }


}
