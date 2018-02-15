package com.example.smo.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class LoginActivity extends Activity {
    protected static final String ACTIVITY_NAME="LoginActivity";
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        //save the email the user enters
        prefs = getSharedPreferences("UserInput", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit(); //edit the file

        int numLaunches = prefs.getInt("NumRuns", 0);
        edit.putInt("NumRuns", numLaunches + 1); //ran one more time
         edit.commit();
        //edit.apply();

        /*reference to the login button*/
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
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
