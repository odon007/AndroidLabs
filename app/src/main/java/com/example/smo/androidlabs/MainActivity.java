package com.example.smo.androidlabs;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends Activity {
    protected static final String ACTIVITY_NAME="MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //reference to button on main activity
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               //when button is clicked launch ListItemsActivity
               launchListItemsActivity();
            }
        });

        //launch ChatWindow when button is clicked
        Button chatButton = findViewById(R.id.chatButton);
        chatButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchChatWindow();
            }
        }));
    } //end onCreate

    protected void launchListItemsActivity(){
        Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
        startActivityForResult(intent, 50);
    }

    protected void launchChatWindow(){
        Log.i(ACTIVITY_NAME, "User clicked Start Chat");
        Intent intent = new Intent(MainActivity.this, ChatWindow.class);
        startActivityForResult(intent, 50);
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent data){
        if(requestCode == 50){
            Log.i(ACTIVITY_NAME, "Returned to MainActivity.onActivityResult");
        }
    }
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
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
