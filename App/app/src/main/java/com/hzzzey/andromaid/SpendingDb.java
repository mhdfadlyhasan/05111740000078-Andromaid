package com.hzzzey.andromaid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.hzzzey.andromaid.SpendingContract.SpendingEntry;


public class SpendingDb extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Spending.db";
    private static SpendingDb ourInstance;

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + SpendingEntry.TABLE_NAME + " (" +
            SpendingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            SpendingEntry.SPENDING_AMOUNT + " TEXT," +
            SpendingEntry.SPENDING_DATE_TIME + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
    //todo untuk akses waktu, kamu harus akese datetime(Spending_Date_Time, 'localtime') so far belum nemu format langsung

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SpendingEntry.TABLE_NAME;

    public static SpendingDb getInstance(Context context)//singleton database
    {
        if (ourInstance == null)
            ourInstance = new SpendingDb(context);
        return ourInstance;
    }
    public SpendingDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

}

