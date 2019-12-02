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
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText mInputEmail;
    private EditText mInputPassword;
    private Button mLoginButton;
    private TextView mCreateAccountLink;

    // Create reference to Firebase mAuth
    private FirebaseAuth mAuth;

    // Create reference to mAuthStateListener
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                finish();
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
}


/*
package com.duarte.androidproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements FirebaseInterface {

//  TODO: Add forget password feature

    TextView txvEmail;
    TextView txvPassword;
    String strEmail;
    String strPassword;
    Student student;

    private FirebaseAuth mAuth;

    Button loginButton;

    //DatabaseHelper dbHelper;
    FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD

        mAuth = FirebaseAuth.getInstance();
=======
        firebaseHelper = new FirebaseHelper();
>>>>>>> 7ed8b5ad65ca34045643ad3b4313f4bb66b298fc
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    //Respond to Onclick from "create an account"
    public void register(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    //Respond to OnClick from "Login"
    public void login(View view) {

        if (Global.debug) {
//            strEmail = "Juyeong_Seo@student.uml.edu";
//            strPassword = "tjtjtj";
            strEmail = "Test@yahoo.com";
            strPassword = "Duarte";
        } else {
            txvEmail = findViewById(R.id.main_User);
            txvPassword = findViewById(R.id.main_Password);
            strEmail = txvEmail.getText().toString();
            strPassword = txvPassword.getText().toString();
        }


        //check if user has entered a username and password
        if (strEmail.isEmpty()) {
            Toast.makeText(MainActivity.this.getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            Log.println(Log.DEBUG, "Log", "missing username");
            return;
        }
        if (strPassword.isEmpty()) {
            Toast.makeText(MainActivity.this.getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            Log.println(Log.DEBUG, "Log", "missing password");
            return;
        }


        firebaseHelper.validateStudentLogin(this);

    }//end of login(View view)

    //sign in existing users
    private void signIn(String email, String password) {

<<<<<<< HEAD
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(String.valueOf(Log.DEBUG), "signInWithEmail:" + task.isSuccessful());

                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(String.valueOf(Log.DEBUG), "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

        @Override
        public void onSuccess (Task < QuerySnapshot > task) {
            for (QueryDocumentSnapshot document : task.getResult()) {
                HashMap<String, Object> current = (HashMap<String, Object>) document.getData();
                if (current.get("email").equals(strEmail)) {
                    if (current.get("password").equals(strPassword)) {
                        student = new Student(current);
                        Log.println(Log.DEBUG, "log", "Login successful");
                        Intent intent = new Intent(this, HomePage.class);
                        Bundle bundle = new Bundle();

                        //We pass student obj to following activity so we know which user is logged in
                        bundle.putSerializable("objStudent", student);
                        intent.putExtras(bundle);
=======
    @Override
    public void onSuccess(Task<QuerySnapshot> task){
        for (QueryDocumentSnapshot document : task.getResult()) {
            HashMap<String, Object> current = (HashMap<String, Object>) document.getData();
            if(current.get("email").equals(strEmail)){
                if(current.get("password").equals(strPassword)) {
                    student = new Student(current);
                    student.setDocId(document.getId());
                    Log.println(Log.DEBUG, "log", "Login successful");
                    Intent intent = new Intent(this, HomePage.class);
                    Bundle bundle = new Bundle();

                    //We pass student obj to following activity so we know which user is logged in
                    bundle.putSerializable("objStudent", student);
                    intent.putExtras(bundle);

                    //start HomePage activity
                    startActivity(intent);
>>>>>>> 7ed8b5ad65ca34045643ad3b4313f4bb66b298fc

                        //start HomePage activity
                        startActivity(intent);

                    } else {
                        Log.println(Log.DEBUG, "Log", "Login was not successful");
                        return;
                    }
                    break;
                }
            }
        }

        @Override
        public void onFailed (Task < QuerySnapshot > data) {
            Log.println(Log.DEBUG, "Log", "ERROR has occurred getting student");
        }

        public void updateUI (FirebaseUser account){
            if (account != null) {
                Toast.makeText(this, "Signed In successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, HomePage.class));
            } else {
                Toast.makeText(this, "Didn't sign in", Toast.LENGTH_LONG).show();
            }
        }
    }


 */
