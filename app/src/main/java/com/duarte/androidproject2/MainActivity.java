package com.duarte.androidproject2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;


/**
 * MainActivity.java handles logging in a user who already has an account.
 * <p>
 * Before:
 * User provides their account Email and Password to login
 * After:
 * If successful, user is logged into their account and is present with Main screen
 * Other:
 * If user doesn't have an account, they can be taken to SignUp screen
 */
public class MainActivity extends AppCompatActivity implements FirebaseInterface {
    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText mInputEmail;
    private EditText mInputPassword;
    private Button mLoginButton;
    private TextView mCreateAccountLink;

    // Create reference to Firebase mAuth
    private FirebaseAuth mAuth;

    // Create reference to mAuthStateListener
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    FirebaseHelper firebaseHelper;

    //hate that I have to do it like this, but can't call it inside onAuthStateChanged, because
    //I need "this" from MainActivity
    public void callFirebaseHelperFunction(String email){
        //will call interfaceFunction validateStundetHelper, which will register stundet email
        //to firestore if it is not already registerd. Had to do it like this because onAuthStateChanged
        //and onComplete are async functions and act weird
        firebaseHelper.validateStudentLogin(this, email );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseHelper = new FirebaseHelper();

        // Views
        mInputEmail = (EditText) findViewById(R.id.main_User);
        mInputPassword = (EditText) findViewById(R.id.main_Password);

        // Buttons
        mLoginButton = (Button) findViewById(R.id.main_loginBtn);
        mCreateAccountLink = (TextView) findViewById(R.id.link_create_account);

        // Initialize Firebase mAuth object
        mAuth = FirebaseAuth.getInstance();

        // Create AuthStateListener to respond to changes in user signin state
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    callFirebaseHelperFunction(user.getEmail().toString());
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    // Send user to Homepage activity
                    Intent intent = new Intent(MainActivity.this, HomePage.class);
                    startActivity(intent);

                    // Call to destroy an activity
                    finish();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                updateUI(user);
            }
        };

        // Listener for Login button
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If successful, take user to initial screen.
                // Otherwise show an error
                signIn(mInputEmail.getText().toString(), mInputPassword.getText().toString());
            }
        });

        // Listener for Create Account
        mCreateAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the signup activity
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);

            }
        });
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

    // Sign in user
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        // Sign in user with email and password
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user.
                        // If sign in succeeds the auth state listener will be notified
                        // and logic to handle the signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "Welcome back " + mAuth.getCurrentUser().getEmail(),
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
        boolean valid = true;

        // Validate Email, and Password
        String email = mInputEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mInputEmail.setError(getString(R.string.error_field_required));
            valid = false;
        } else {
            mInputEmail.setError(null);
        }

        String password = mInputPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mInputPassword.setError(getString(R.string.error_field_required));
            valid = false;
        } else {
            mInputPassword.setError(null);
        }

        return valid;
    }

    @Override
    public void validateStundetHelper(Task <QuerySnapshot> task, String email) {
        boolean studentAlreadyInFirestore = false;
        for (QueryDocumentSnapshot document : task.getResult()) {
            HashMap<String, Object> current = (HashMap<String, Object>) document.getData();

            if (current.get("email").equals(email)) {
                 studentAlreadyInFirestore = true;
                Log.println(Log.DEBUG, "Log", "Student already in firestore");
            }
        }
        if(!studentAlreadyInFirestore) {
            firebaseHelper.registerStudentEmailFirebase(email);
            Log.println(Log.DEBUG, "Log", "Adding Student to firestore");
        }

    }

    @Override
    public void onFailed (Task < QuerySnapshot > data) {
        Log.println(Log.DEBUG, "Log", "ERROR has occurred getting student");
    }
}

