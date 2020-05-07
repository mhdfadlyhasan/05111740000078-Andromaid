package com.hzzzey.andromaid;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class InputEditSchedule extends AppCompatActivity implements TimePickerFragment.Listeners, AdapterView.OnItemSelectedListener {

    Spinner schedule_day;
    Button picktime;
    int dayspicked;
    EditText schedule_name,schedule_location,schedule_description;
    String time;
    User user = User.getInstance();
    ScheduleDb dbSchedule = ScheduleDb.getInstance(this);
    SQLiteDatabase db = dbSchedule.getWritableDatabase();
    String [] days = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input_edit_schedule);
        picktime = findViewById(R.id.pick_time);
        // Create an ArrayAdapter using the string array and a default spinner layout

        schedule_day = findViewById(R.id.spinner_day);
        schedule_day.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, days);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        schedule_day.setAdapter(adapter);
        schedule_day.setSelection(user.cal.get(Calendar.DAY_OF_WEEK)-1);
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        dayspicked = position + 1;
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onClick(View v)
    {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show( getFragmentManager(), "timePicker");
    }

    public void finish(View v)
    {
        schedule_name = findViewById(R.id.names);
        schedule_location = findViewById(R.id.location);
        schedule_description = findViewById(R.id.descriptions);

        ContentValues values = new ContentValues();
        values.put(ScheduleDb.ScheduleEntry.TASK_NAME, schedule_name.getText().toString());
        values.put(ScheduleDb.ScheduleEntry.TASK_PLACE, schedule_location.getText().toString());
        values.put(ScheduleDb.ScheduleEntry.TASK_DESCRIPTION, schedule_description.getText().toString());
        values.put(ScheduleDb.ScheduleEntry.TASK_DATE, dayspicked);
        values.put(ScheduleDb.ScheduleEntry.TASK_TIME, time);
        db.insert(ScheduleDb.ScheduleEntry.TABLE_NAME,null,values);
    }

    public void setTime(int hourOfDay,int minute)
    {
        time = String.format("%s.%s",hourOfDay,minute);
        picktime.setText(time);
    }
}
