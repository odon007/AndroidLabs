package com.example.smo.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
    protected static final String ACTIVITY_NAME="LoginActivity";
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        final EditText emailText = findViewById(R.id.emailText);
        final EditText password = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.loginButton);

        //save the email the user enters
        prefs = getSharedPreferences("UserInput", Context.MODE_PRIVATE);
        String email = prefs.getString("emailText", "email@domain.com");
        emailText.setText(email);
        //SharedPreferences.Editor edit = prefs.edit(); //edit the file

        /*reference to the login button*/
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("emailText", emailText.getText().toString());
                editor.putString("password", password.getText().toString());
                editor.commit();
            //when button is clicked launch MainActivity
                launchMainActivity();
            }
        });

    }
    protected void launchMainActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivityForResult(intent, 50);
    }
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
//prefs = getSharedPreferences(edit, Context.MODE_PRIVATE);
        prefs = this.getPreferences(Context.MODE_PRIVATE);
    }

    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }



}
