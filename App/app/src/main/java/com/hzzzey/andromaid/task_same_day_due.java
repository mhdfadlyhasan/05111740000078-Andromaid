package com.hzzzey.andromaid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.List;

import ca.antonious.materialdaypicker.MaterialDayPicker;

public class task_same_day_due extends AppCompatActivity implements Adapter_List_and_Task.onTaskScheduleLongClick, DialogTaskSchedulePrompt.DialogTaskScheduleListener {
    User user = User.getInstance();
    MaterialDayPicker.Weekday days;
    TaskDb TaskDbhelper;
    RecyclerView recyclerViewsss;
    RecyclerView.Adapter mAdapter;
    List<task_schedule_item> task;
    public int itemSelected;
    public List<task_schedule_item> list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TaskDbhelper = TaskDb.getInstance(this);
        setContentView(R.layout.activity_weekly_schedule);
        MaterialDayPicker dayPicker = findViewById(R.id.day_picker);
        days = getDate(user.cal.get(Calendar.DAY_OF_WEEK));
        dayPicker.setSelectedDays(days);
        dayPicker.setDayPressedListener(new MaterialDayPicker.DayPressedListener() {
            @Override
            public void onDayPressed(MaterialDayPicker.Weekday weekday, boolean b) {
                changeDay(weekday);
            }
        });
        recyclerViewsss = findViewById(R.id.recycler_detailed);
        recyclerViewsss.setHasFixedSize(true);
        task = TaskDbhelper.getList(user.cal.get(Calendar.DAY_OF_WEEK));
        mAdapter = new Adapter_List_and_Task(task, this);
        recyclerViewsss.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewsss.setLayoutManager(layoutManager);
    }
    public void onClick(View v)
    {
        startActivityForResult(new Intent(getApplicationContext(),InputEditSchedule.class),0);
    }
    private MaterialDayPicker.Weekday getDate(int i)
    {
        switch (i)
        {
            case 2:
                return MaterialDayPicker.Weekday.MONDAY;
            case 3:
                return MaterialDayPicker.Weekday.TUESDAY;
            case 4:
                return MaterialDayPicker.Weekday.WEDNESDAY;
            case 5:
                return MaterialDayPicker.Weekday.THURSDAY;
            case 6:
                return MaterialDayPicker.Weekday.FRIDAY;
            case 7:
                return MaterialDayPicker.Weekday.SATURDAY;
            case 1:
                return MaterialDayPicker.Weekday.SUNDAY;
            default:
                return MaterialDayPicker.Weekday.FRIDAY;
        }
    }
    private void changeDay(MaterialDayPicker.Weekday weekdays)
    {
        int i;
        switch (weekdays)
        {
            case MONDAY:
                Log.d("test", "Makkau");
                i=2;
                break;
            case TUESDAY:
                i=3;
                break;
            case WEDNESDAY:
                i=4;
                break;
            case THURSDAY:
                i=5;
                break;
            case FRIDAY:
                Log.d("test", "Makkau");
                i=6;
                break;
            case SATURDAY:
                i=7;
                break;
            case SUNDAY:
                i=1;
                break;
            default:
                i=9;
                Log.d("test", "What, Called?");
                break;
        }
        task.clear();
        task.addAll(TaskDbhelper.getList(i));
        Log.d("test", weekdays + " " + task.size());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemLongClick(int position) {
        Log.d("test", position + "");
        itemSelected = position;
        DialogTaskSchedulePrompt dialogTaskSchedulePrompt = new DialogTaskSchedulePrompt();
        dialogTaskSchedulePrompt.show(getSupportFragmentManager(), "ok");
    }
    @Override
    public List<task_schedule_item> getlist() {
        return task;
    }
    @Override
    public int getPosition() {
        return itemSelected;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent lazy = new Intent(this,Weekly_Schedule.class);
        finish();
        startActivity(lazy);
    }
}
