package com.hzzzey.andromaid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.List;


public class DialogTaskSchedulePrompt extends DialogFragment {
    public DialogTaskScheduleListener listener;
    task_schedule_item item;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.layout_schedule_and_task, null);
        builder.setView(view);
        TextView Name = view.findViewById(R.id.item_name);
        TextView Time = view.findViewById(R.id.item_time);
        TextView Location = view.findViewById(R.id.item_location);
        TextView Description = view.findViewById(R.id.item_description);
        List <task_schedule_item> list = listener.getlist();
        int itemSelected = listener.getPosition();
        item = list.get(itemSelected);
        Name.setText(item.getName());
        Time.setText(item.getTime());
        Location.setText(item.getPlace());
        Description.setText(item.getDescription());
        // Set the dialog title
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if(item.getType() == 0) // schedule
                {
                    ScheduleDb dbSchedule = ScheduleDb.getInstance(getContext());
                    SQLiteDatabase db = dbSchedule.getWritableDatabase();
                    String selection = ScheduleDb.ScheduleEntry._ID + " = " + item.getId();
                    db.delete(ScheduleDb.ScheduleEntry.TABLE_NAME, selection, null);
                }
                else
                {
                    TaskDb taskDb = TaskDb.getInstance(getContext());
                    SQLiteDatabase db = taskDb.getWritableDatabase();
                    String selection = TaskDb.TaskEntry._ID + " = " + item.getId();
                    db.delete(TaskDb.TaskEntry.TABLE_NAME, selection, null);
                }
            }
        }).setNegativeButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent newIntent;
                Log.d("test", item.getType() +"");
                if(item.getType() == 0) newIntent = new Intent(getContext(),EditSchedule.class);//Schedule
                else newIntent = new Intent(getContext(),EditTask.class);
                newIntent.putExtra("ID_item", item.getId());
                startActivity(newIntent);
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogTaskScheduleListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " Implement the listener!");
        }
    }

    public interface DialogTaskScheduleListener
    {
        List <task_schedule_item> getlist();
        int getPosition();
    }
}