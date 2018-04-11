package com.example.smo.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MessageFragment extends Fragment {
    protected static final String ACTIVITY_NAME = "MessageFragment";

    TextView msgText, idText;
    Button deleteButton;
    Bundle getInfo;
    Boolean isTablet;
    Long id;
    private SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       getInfo = getArguments();
    }//end onCreate

    //this only runs on the tablet
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState){
        View page = layoutInflater.inflate(R.layout.activity_message_fragment, null);
        final ChatDatabaseHelper chatDatabaseHelper = new ChatDatabaseHelper(getActivity());
        db = chatDatabaseHelper.getWritableDatabase();

        msgText = page.findViewById(R.id.msgText);
        msgText.setText("The message is " + getInfo.getString("data message"));
        idText = page.findViewById(R.id.idText);
        idText.setText("The ID is " +  getInfo.getLong("id"));

        isTablet = getInfo.getBoolean("isTablet");
        id = getInfo.getLong("id");

        deleteButton = page.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isTablet){
                    db.delete(ChatDatabaseHelper.TABLE_NAME, chatDatabaseHelper.KEY_id + "=" + id, null);
                    getActivity().finish();
                    Intent intent = getActivity().getIntent();
                    startActivity(intent);
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("id", id);
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                }
            }
        });
        return page;
    }
}
