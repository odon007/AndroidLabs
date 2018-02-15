package com.example.smo.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.content.Intent;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends Activity {
    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    ImageButton imageButton;
    Switch switch1;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME, "In onCreate()");

         imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //when imageButton is clicked launch photo application
                final int REQUEST_IMAGE_CAPTURE = 1;

                //method to launch photo app and take a picture
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 50);
                } //end if
            }

            }); //end imageButton onClick

                //change switch object
                switch1 = findViewById(R.id.switch1);
               // switch1.setChecked(true);
               switch1.setOnClickListener(new View.OnClickListener() {
                   Context context = getApplicationContext();
                   CharSequence text;
                   int duration = Toast.LENGTH_SHORT;
                   @Override
                   public void onClick(View v) {
                       if(switch1.isChecked()){
                           text = "Switch is On";// "Switch is Off"

                          // Toast toast = Toast.makeText(context, text, duration); //this is the ListActivity
                        //   toast.show(); //display your message box
                       }else if(!(switch1.isChecked())){
                            text = "Switch is OFF";// "Switch is Off"


                       }
                       Toast toast = Toast.makeText(context, text, duration); //this is the ListActivity
                       toast.show(); //display your message box
                   }
               }

               );

                //checkbox object
                checkBox = findViewById(R.id.checkBox);
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                        //chain together setters to set the dialog characteristics
                        builder.setMessage(R.string.dialog_message) //in strings.xml
                                .setTitle(R.string.dialog_title)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //user clicked OK button
                                        Intent resultIntent = new Intent();
                                        resultIntent.putExtra("Response", "Here is my response");
                                        setResult(Activity.RESULT_OK, resultIntent);
                                        finish();
                                    }
                                })
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //user cancelled dialog
                                    }
                                })
                                .show();
                    }
                });

        } //end onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //get the photo and save it on the imageButton
      //  int requestCode = 0, resultCode = 0;

        if (requestCode == requestCode && resultCode == RESULT_OK) {
            Bundle extras =data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton.setImageBitmap(imageBitmap);
        } //end if
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
