package com.duarte.androidproject2;

        import androidx.appcompat.app.AppCompatActivity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

//  TODO: Add forget password feature

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //Respond to Onclick from "create an account"
    public void register(View view){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
    //Respond to OnClick from "Login"
    public void login(View view) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

}

