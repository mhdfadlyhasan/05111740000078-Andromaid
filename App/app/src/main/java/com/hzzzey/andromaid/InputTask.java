package com.hzzzey.andromaid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

public class InputTask extends AppCompatActivity implements
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_task);

        picktime = findViewById(R.id.pick_time);
        // Create an ArrayAdapter using the string array and a default spinner layout
        pickdays = findViewById(R.id.Days);
        task_importance = findViewById(R.id.spinner_importance);
        task_importance.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, days);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        task_importance.setAdapter(adapter);
        task_importance.setSelection(0);
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
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                InputTask.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
    }

    public void finish(View v)
    {
        task_name = findViewById(R.id.names);
        task_location = findViewById(R.id.location);
        task_description = findViewById(R.id.descriptions);
        ContentValues values = new ContentValues();
        values.put(TaskDb.TaskEntry.TASK_NAME, task_name.getText().toString());
        values.put(TaskDb.TaskEntry.TASK_PLACE, task_location.getText().toString());
        values.put(TaskDb.TaskEntry.TASK_DESCRIPTION, task_description.getText().toString());
        values.put(TaskDb.TaskEntry.TASK_IMPORTANCE, importance_picked);
        values.put(TaskDb.TaskEntry.TASK_TIME, picktime.getText().toString());
        values.put(TaskDb.TaskEntry.TASK_DATE, pickdays.getText().toString());
        db.insert(TaskDb.TaskEntry.TABLE_NAME,null,values);
        setResult(1,null);
        finish();
    }
    public void setTime(int hourOfDay,int minute)
    {
        time = String.format("%s.%s",hourOfDay,minute);
        picktime.setText(time);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        Log.d("test", "succed" + date);
        if(monthOfYear < 10)pickdays.setText(String.format("%s-0%s-%s",year,monthOfYear+1,dayOfMonth));
        else pickdays.setText(String.format("%s-%s-%s",year,monthOfYear+1,dayOfMonth));
    }
}
