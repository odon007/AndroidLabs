package com.example.smo.androidlabs;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MessageDetails extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "MessageDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

         Bundle infoToPass = getIntent().getExtras();
         FragmentManager fM = getFragmentManager();
         FragmentTransaction fT = fM.beginTransaction();
         MessageFragment msgF = new MessageFragment();

         msgF.setArguments(infoToPass);
         fT.addToBackStack(null); //will undo FT when back button is clicked
         fT.replace(R.id.detailsFrameLayout, msgF);
         fT.commit();
    } //end onCreate
}
