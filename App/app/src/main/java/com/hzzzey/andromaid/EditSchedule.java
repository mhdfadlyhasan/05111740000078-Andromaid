package com.hzzzey.andromaid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class EditSchedule extends AppCompatActivity implements  TimePickerFragment.Listeners, AdapterView.OnItemSelectedListener  {
    Spinner schedule_day;
    Button picktime;
    int dayspicked;
    EditText schedule_name,schedule_location,schedule_description;
    String time;
    User user = User.getInstance();
    ScheduleDb dbSchedule = ScheduleDb.getInstance(this);
    SQLiteDatabase db = dbSchedule.getWritableDatabase();
    String [] days = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    int date;
    int id;
    String Place, Description, Time, Name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);
        picktime = findViewById(R.id.pick_time);
        // Create an ArrayAdapter using the string array and a default spinner layout
        Bundle extra = getIntent().getExtras();

        id = extra.getInt("ID_item");
        Cursor cursors = db.rawQuery
                ("Select _id, Name, Description, Place,Time,Task_Date " +
                        "from Schedule where _id="+ id +" " ,null);
        if(cursors.moveToFirst()) {
            Name = cursors.getString(cursors.getColumnIndexOrThrow("Name"));
            Place = cursors.getString(cursors.getColumnIndexOrThrow("Place"));
            Description = cursors.getString(cursors.getColumnIndexOrThrow("Description"));
            Time = cursors.getString(cursors.getColumnIndexOrThrow("Time"));
            date = cursors.getInt(cursors.getColumnIndexOrThrow("Task_Date"));
        }
        else
        {
            return;
        }
        schedule_day = findViewById(R.id.spinner_day);
        schedule_day.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, days);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        schedule_day.setAdapter(adapter);
        schedule_day.setSelection(date-1);

        schedule_name = findViewById(R.id.names);
        schedule_location = findViewById(R.id.location);
        schedule_description = findViewById(R.id.descriptions);
        schedule_name.setText(Name);
        schedule_location.setText(Place);
        schedule_description.setText(Description);
        picktime.setText(Time);
    }

    public void onClick(View v)
    {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show( getFragmentManager(), "timePicker");
    }

    public void finish(View v)
    {

        ContentValues values = new ContentValues();
        values.put(ScheduleDb.ScheduleEntry.TASK_NAME, schedule_name.getText().toString());
        values.put(ScheduleDb.ScheduleEntry.TASK_PLACE, schedule_location.getText().toString());
        values.put(ScheduleDb.ScheduleEntry.TASK_DESCRIPTION, schedule_description.getText().toString());
        values.put(ScheduleDb.ScheduleEntry.TASK_DATE, dayspicked);
        values.put(ScheduleDb.ScheduleEntry.TASK_TIME, time);

        String selection = ScheduleDb.ScheduleEntry._ID + " = " + id;
        long yeet = db.update(ScheduleDb.ScheduleEntry.TABLE_NAME, values, selection,null);
        Toast.makeText(this,"Edited " + schedule_name.getText().toString() , Toast.LENGTH_SHORT).show();
        setResult(1,null);
    }

    public void setTime(int hourOfDay,int minute)
    {
        time = String.format("%s.%s",hourOfDay,minute);
        picktime.setText(time);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        dayspicked = position + 1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
