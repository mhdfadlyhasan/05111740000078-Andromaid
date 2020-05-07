package com.hzzzey.andromaid;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskDb extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Task.db";
    private static TaskDb ourInstance;

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TaskEntry.TABLE_NAME + " (" +
            TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TaskEntry.TASK_NAME + " TEXT," +
            TaskEntry.TASK_DESCRIPTION + " TEXT DEFAULT '-'," +
            TaskEntry.TASK_PLACE + " TEXT DEFAULT '-'," +
            TaskEntry.TASK_IMPORTANCE + " TINYINT," +
            TaskEntry.TASK_TIME + " TEXT," +
            TaskEntry.TASK_DATE + " TIMESTAMP)";
    //todo untuk akses waktu, kamu harus akese datetime(Spending_Date_Time, 'localtime') so far belum nemu format langsung

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TaskEntry.TABLE_NAME;

    public static TaskDb getInstance(Context context)//singleton database
    {
        if (ourInstance == null)
            ourInstance = new TaskDb(context);
        return ourInstance;
    }
    public TaskDb(Context context) {
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
    public final class TaskEntry implements BaseColumns
    {
        static final String TABLE_NAME = "Task";
        static final String TASK_NAME = "Name";
        static final String TASK_DESCRIPTION = "Description";
        static final String TASK_PLACE = "Place";
        static final String TASK_TIME = "Task_Time";
        static final String TASK_DATE = "Task_Date";
        static final String TASK_IMPORTANCE = "Task_Importance";
    }
    public List<task_schedule_item> getListToday()
    {
        final int SCHEDULE = 0;
        final int TASK = 1;
        Calendar cal = Calendar.getInstance();
        Date temp = cal.getTime();
        String Date = (String) DateFormat.format("yyyy-M-d", temp); // date
        Log.d("test", Date);
        SQLiteDatabase dbScheduleRead = this.getReadableDatabase();
        List Schedule = new ArrayList<task_schedule_item>();
        Cursor cursors = dbScheduleRead.rawQuery
                ("Select _id, Name, Description, Place,Task_Time " +
                        "from Task " +
                        "where Task_date = '"+ Date +"' " ,null);
        while(cursors.moveToNext()) {
            Log.d("test", "succed");
            int id = cursors.getInt(cursors.getColumnIndexOrThrow("_id"));
            String name = cursors.getString(cursors.getColumnIndexOrThrow("Name"));
            String description = cursors.getString(cursors.getColumnIndexOrThrow("Description"));
            String Place = cursors.getString(cursors.getColumnIndexOrThrow("Place"));
            String Time = cursors.getString(cursors.getColumnIndexOrThrow("Task_Time"));
            Schedule.add(new task_schedule_item(id,name,description,Place,Time,TASK,null));
        }
        cursors.close();
        return Schedule;
    }
    public List<task_schedule_item> getAllTaskList()
    {
        final int TASK = 1;
        Calendar cal = Calendar.getInstance();
        Date temp = cal.getTime();
        String Date = (String) DateFormat.format("yyyy-M-d", temp); // date
        Log.d("test", Date);
        SQLiteDatabase dbScheduleRead = this.getReadableDatabase();
        List Schedule = new ArrayList<task_schedule_item>();
        Cursor cursors = dbScheduleRead.rawQuery
                ("Select _id, Name, Description, Place,Task_Time,Task_Date " +
                        "from Task "
                        ,null);
        while(cursors.moveToNext()) {
            Log.d("test", "succed");
            int id = cursors.getInt(cursors.getColumnIndexOrThrow("_id"));
            String name = cursors.getString(cursors.getColumnIndexOrThrow("Name"));
            String description = cursors.getString(cursors.getColumnIndexOrThrow("Description"));
            String Place = cursors.getString(cursors.getColumnIndexOrThrow("Place"));
            String Time = cursors.getString(cursors.getColumnIndexOrThrow("Task_Time"));
            String Dates = cursors.getString(cursors.getColumnIndexOrThrow("Task_Date"));
            Schedule.add(new task_schedule_item(id,name,description,Place,Time,TASK,Dates));
        }
        cursors.close();
        return Schedule;
    }
}
