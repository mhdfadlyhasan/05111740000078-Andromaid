package com.hzzzey.andromaid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Debug;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ScheduleFragment extends Fragment implements Adapter_List_and_Task.onTaskScheduleLongClick{
    // TODO: Rename parameter arguments, choose names that match
    // TODO: Rename and change types of parameters
    ScheduleDb ScheduledbHelper;
    TaskDb taskdbHelper;
    SQLiteDatabase dbScheduleRead, dbScheduleWrite;
    SQLiteDatabase dbTaskRead, dbTaskWrite;
    User user = User.getInstance();
    public int itemSelected;
    public List<task_schedule_item> list;
    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_schedule_and_task, container, false);
        ScheduledbHelper = ScheduleDb.getInstance(getContext());
        taskdbHelper = TaskDb.getInstance(getContext());


        dbScheduleRead = ScheduledbHelper.getReadableDatabase();
        dbScheduleWrite = ScheduledbHelper.getWritableDatabase();

        dbTaskRead = taskdbHelper.getReadableDatabase();
        dbTaskWrite = taskdbHelper.getWritableDatabase();

        RecyclerView recyclerViewsss = root.findViewById(R.id.recyclerViewsss);
        recyclerViewsss.setHasFixedSize(true);
        RecyclerView.Adapter mAdapter;
        list  = taskdbHelper.getListToday();
        list.addAll(ScheduledbHelper.getList(user.cal.get(Calendar.DAY_OF_WEEK)));
        mAdapter = new Adapter_List_and_Task(list, null);
        recyclerViewsss.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        recyclerViewsss.setLayoutManager(layoutManager);
        return root;
    }

    @Override
    public void onItemLongClick(int position) {

    }
}

