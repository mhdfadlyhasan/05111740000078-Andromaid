package com.hzzzey.andromaid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDb extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 3;
        public static final String DATABASE_NAME = "Schedule.db";
        private static ScheduleDb ourInstance;

        private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + ScheduleEntry.TABLE_NAME + " (" +
                ScheduleEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ScheduleEntry.TASK_NAME + " TEXT," +
                ScheduleEntry.TASK_DESCRIPTION + " TEXT DEFAULT '-'," +
                ScheduleEntry.TASK_PLACE + " TEXT DEFAULT '-'," +
                ScheduleEntry.TASK_TIME + " TEXT DEFAULT '-'," +
                ScheduleEntry.TASK_DATE + " INT)";
        //todo untuk akses waktu, kamu harus akese datetime(Spending_Date_Time, 'localtime') so far belum nemu format langsung

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + ScheduleEntry.TABLE_NAME;

        public static ScheduleDb getInstance(Context context)//singleton database
        {
            if (ourInstance == null)
                ourInstance = new ScheduleDb(context);
            return ourInstance;
        }
        public ScheduleDb(Context context) {
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
        public static final class ScheduleEntry implements BaseColumns
        {
            static final String TABLE_NAME = "Schedule";
            static final String TASK_NAME = "Name";
            static final String TASK_DESCRIPTION = "Description";
            static final String TASK_PLACE = "Place";
            static final String TASK_DATE = "Task_Date";
            static final String TASK_TIME = "Time";
        }
    public List<task_schedule_item> getList(int day)
    {
        final int SCHEDULE = 0;
        final int TASK = 1;
        SQLiteDatabase dbScheduleRead = this.getReadableDatabase();
        List Schedule = new ArrayList<task_schedule_item>();
        Cursor cursors = dbScheduleRead.rawQuery
                ("Select _id, Name, Description, Place,Time " +
                        "from Schedule where Task_date='"+ day +"' " ,null);
        while(cursors.moveToNext()) {
            int id = cursors.getInt(cursors.getColumnIndexOrThrow("_id"));
            String name = cursors.getString(cursors.getColumnIndexOrThrow("Name"));
            String description = cursors.getString(cursors.getColumnIndexOrThrow("Description"));
            String Place = cursors.getString(cursors.getColumnIndexOrThrow("Place"));
            String Time = cursors.getString(cursors.getColumnIndexOrThrow("Time"));
            Schedule.add(new task_schedule_item(id,name,description,Place,Time,SCHEDULE,null));
        }
        cursors.close();
        return Schedule;
    }
}