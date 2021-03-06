package com.hzzzey.andromaid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class EditTask extends AppCompatActivity implements
        TimePickerFragment.Listeners, AdapterView.OnItemSelectedListener,
        DatePickerDialog.OnDateSetListener{

    Spinner task_importance;
    Button picktime,pickdays;
    int importance_picked;
    EditText task_name,task_location,task_description;
    String time;
    User user = User.getInstance();
    TaskDb dbTask = TaskDb.getInstance(this);
    SQLiteDatabase db = dbTask.getWritableDatabase();
    String [] days = {"Not Important","Slightly Important","Important","Very Important","Super Important","I am Gonna Die if I don't do it"};

    String date;
    int id,Task_Importance;
    String Place, Description, Time, Name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_task);
        task_name = findViewById(R.id.names);
        task_location = findViewById(R.id.location);
        task_description = findViewById(R.id.descriptions);
        Bundle contets = getIntent().getExtras();
        id = contets.getInt("ID_item");
        picktime = findViewById(R.id.pick_time);
        // Create an ArrayAdapter using the string array and a default spinner layout
        pickdays = findViewById(R.id.Days);
        task_importance = findViewById(R.id.spinner_importance);
        task_importance.setOnItemSelectedListener(this);

        Cursor cursors = db.rawQuery
                ("Select _id, Name, Description, Place,Task_Date, Task_Importance, Task_Time, Task_Date " +
                        "from Task where _id="+ id +" " ,null);
        if(cursors.moveToFirst()) {
            Name = cursors.getString(cursors.getColumnIndexOrThrow("Name"));
            Place = cursors.getString(cursors.getColumnIndexOrThrow("Place"));
            Description = cursors.getString(cursors.getColumnIndexOrThrow("Description"));
            Time = cursors.getString(cursors.getColumnIndexOrThrow("Task_Time"));
            date = cursors.getString(cursors.getColumnIndexOrThrow("Task_Date"));
            Task_Importance = cursors.getInt(cursors.getColumnIndexOrThrow("Task_Importance"));
            Log.d("test", Name);
        }
        else
        {
            return;
        }
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, days);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        task_importance.setAdapter(adapter);
        task_importance.setSelection(Task_Importance-1);
        task_name.setText(Name);
        task_location.setText(Place);
        task_description.setText(Description);
        picktime.setText(Time);
        pickdays.setText(date);
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        importance_picked = position + 1;
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onClick(View v)
    {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show( getFragmentManager(), "timePicker");
    }

    public void onClick2(View v)
    {
        Calendar now = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                EditTask.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
    }

    public void finish(View v)
    {
        ContentValues values = new ContentValues();
        values.put(TaskDb.TaskEntry.TASK_NAME, task_name.getText().toString());
        values.put(TaskDb.TaskEntry.TASK_PLACE, task_location.getText().toString());
        values.put(TaskDb.TaskEntry.TASK_DESCRIPTION, task_description.getText().toString());
        values.put(TaskDb.TaskEntry.TASK_IMPORTANCE, importance_picked);
        values.put(TaskDb.TaskEntry.TASK_TIME, picktime.getText().toString());
        values.put(TaskDb.TaskEntry.TASK_DATE, pickdays.getText().toString());
        String selection = ScheduleDb.ScheduleEntry._ID + " = " + id;
        long yeet = db.update(TaskDb.TaskEntry.TABLE_NAME, values, selection,null);
        Toast.makeText(this,"Edited " + task_name.getText().toString() , Toast.LENGTH_SHORT).show();
        setResult(1,null);
        finish();

    }
    public void setTime(int hourOfDay,int minute)
    {
        time = String.format("%s.%s",hourOfDay,minute);
        picktime.setText(time);
    }

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        Log.d("test", "succed" + date);
        pickdays.setText(String.format("%s-%s-%s",year,monthOfYear+1,dayOfMonth));
    }
}

