package com.hzzzey.andromaid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class ChatHistoryDb extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "chathistory.db";
    private static ChatHistoryDb ourInstance;

    public ChatHistoryDb(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    public static ChatHistoryDb getInstance(Context context)//singleton database
    {
        if (ourInstance == null)
        {
            Log.d("KDatabase","terbuat database" + SQL_CREATE_ENTRIES);
            ourInstance = new ChatHistoryDb(context);
        }
        return ourInstance;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public final class ChatEntry implements BaseColumns {
        public static final String TABLE_NAME = "chathistory";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_TIME = "time_sent";
        public static final String COLUMN_NAME_IS_SEND_BY_USER = "is_user_send";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ChatEntry.TABLE_NAME + " (" +
                    ChatEntry._ID + " INTEGER PRIMARY KEY," +
                    ChatEntry.COLUMN_NAME_CONTENT + " TEXT," +
                    ChatEntry.COLUMN_NAME_IS_SEND_BY_USER + " INTEGER," +//todo kalau ga salah bolean itu ga ada, cek lagi
                    ChatEntry.COLUMN_NAME_TIME + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ChatEntry.TABLE_NAME;
}