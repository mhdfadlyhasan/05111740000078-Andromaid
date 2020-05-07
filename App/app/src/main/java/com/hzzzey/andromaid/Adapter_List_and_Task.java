package com.hzzzey.andromaid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_List_and_Task extends RecyclerView.Adapter<Adapter_List_and_Task.ViewHolder> {

    private onTaskScheduleLongClick onItemListener;
    List<task_schedule_item> this_list;
    
    public Adapter_List_and_Task(List<task_schedule_item> list, onTaskScheduleLongClick onItemListener) {
        this_list = list;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_schedule_and_task,parent,false);
        return new ViewHolder(v,onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameText.setText(this_list.get(position).getName());
        holder.locationText.setText("@" + this_list.get(position).getPlace());
        holder.DescriptionText.setText("#" +this_list.get(position).getDescription());
        holder.timeText.setText(this_list.get(position).getTime());
    }


    @Override
    public int getItemCount() {
        return this_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        TextView id,nameText, locationText, DescriptionText , timeText;
        private onTaskScheduleLongClick onItemListener;
        public ViewHolder(View view, onTaskScheduleLongClick onItemListener)
        {
            super(view);
            nameText = view.findViewById(R.id.item_name);
            locationText = view.findViewById(R.id.item_location);
            DescriptionText = view.findViewById(R.id.item_description);
            timeText = view.findViewById(R.id.item_time);
            view.setOnLongClickListener(this);
            this.onItemListener = onItemListener;
        }
        @Override
        public boolean onLongClick(View v) {
            if(onItemListener!=null)onItemListener.onItemLongClick(getAdapterPosition());
            return true;
        }
    }
    public interface onTaskScheduleLongClick
    {
        void onItemLongClick(int position);
    }

}
