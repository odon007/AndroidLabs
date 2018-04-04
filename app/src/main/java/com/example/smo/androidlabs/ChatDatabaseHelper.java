
package com.example.smo.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by SMO on 2018-03-07.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="ChatDatabase";
    public static final int VERSION_NUM = 7;

    protected static final String TABLE_NAME = "ChatTable";
    protected static final String KEY_id = "id";
    protected static final String KEY_MESSAGE = "Message";

    public ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + KEY_id + " INTEGER PRIMARY KEY, "
                + KEY_MESSAGE + ");"
        );

        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("ChatDatabaseHelper","Calling onUpgrade, old version = " + oldVersion + " new version = " + newVersion);
    }
}