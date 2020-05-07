package com.hzzzey.andromaid;

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

public class Tasks extends AppCompatActivity
        implements adapter_task.onTaskScheduleLongClick, DialogTaskSchedulePrompt.DialogTaskScheduleListener {
    User user = User.getInstance();
    TaskDb TaskHelper;
    RecyclerView recyclerViewsss;
    RecyclerView.Adapter mAdapter;
    List<task_schedule_item> schedules;
    public int itemSelected;
    public List<task_schedule_item> list;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TaskHelper = TaskDb.getInstance(this);
        setContentView(R.layout.activity_tasks);
        recyclerViewsss = findViewById(R.id.recycler_detailed);
        recyclerViewsss.setHasFixedSize(true);
        schedules = TaskHelper.getAllTaskList();
        mAdapter = new adapter_task(schedules, this);
        recyclerViewsss.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewsss.setLayoutManager(layoutManager);
    }
    public void onClick(View v)
    {
        startActivity(new Intent(getApplicationContext(),InputEditSchedule.class));
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
        return schedules;
    }
    @Override
    public int getPosition() {
        return itemSelected;
    }
}
