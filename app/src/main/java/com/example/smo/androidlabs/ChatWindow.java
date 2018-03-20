package com.example.smo.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.smo.androidlabs.ChatDatabaseHelper.KEY_MESSAGE;
import static com.example.smo.androidlabs.ChatDatabaseHelper.KEY_id;
import static com.example.smo.androidlabs.ChatDatabaseHelper.TABLE_NAME;

public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindow";

    final ArrayList<String> chatMsgArray = new ArrayList<>(); //stores chat messages

    private static SQLiteDatabase db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        final ListView listView = findViewById(R.id.listView);
        final ChatAdapter messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter); //listView becomes ChatAdapter object

        final ChatDatabaseHelper cdbHelper = new ChatDatabaseHelper(this);
        db = cdbHelper.getWritableDatabase();

        final ContentValues contentValues = new ContentValues();

        final Cursor cursor = db.query(ChatDatabaseHelper.TABLE_NAME, new String[]{ChatDatabaseHelper.KEY_id, ChatDatabaseHelper.KEY_MESSAGE}, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                String message = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
                chatMsgArray.add(message);
                Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                cursor.moveToNext();
            } while(!cursor.isAfterLast());
        }

        Log.i(ACTIVITY_NAME, "Cursor's column count = " + cursor.getColumnCount());
        for(int i = 0; i < cursor.getColumnCount(); i++){
            Log.i(ACTIVITY_NAME, cursor.getColumnName(i));
        }

        Button sendButton2 = findViewById(R.id.sendButton2); //when the SEND button is clicked
        sendButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = findViewById(R.id.editText);
                chatMsgArray.add(editText.getText().toString());
                messageAdapter.notifyDataSetChanged();
                //clear the textView so the editor is ready for a new message

                //contentValues.put("message", editText.toString());
                contentValues.put(KEY_MESSAGE, editText.getText().toString());

                db.insert(ChatDatabaseHelper.TABLE_NAME, null, contentValues);
                editText.setText("");
            }
        });

    } //end onCreate

    class ChatAdapter extends ArrayAdapter<String>{ //inner class

        private ChatAdapter(Context ctx) {
            super(ctx, 0);
        } //ChatAdapter constructor

        //4 functions that ChatAdapter must implement
        public int getCount(){  //returns number of rows in ListView
            //number of strings in array list (chatMsgArray)

            return chatMsgArray.size();
        }

        public String getItem(int position){
            return chatMsgArray.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;

            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = result.findViewById(R.id.message_text);
             message.setText(getItem(position)); //get the string at position

            return result;
        } //end getView

        public long getId(int position){
            return position;
        }

    } //end ChatAdapter

    protected void onDestroy(){
        super.onDestroy();
        db.close();
    }

} //end ChatWindow class