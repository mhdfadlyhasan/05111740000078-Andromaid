package com.hzzzey.andromaid;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_Chat_Input  extends RecyclerView.Adapter<Adapter_Chat_Input.ViewHolder> {
    List<BaseMessage> pesan;
    public Adapter_Chat_Input(List<BaseMessage> dataSet)
    {
        pesan = dataSet;
    }

    @Override
    public int getItemViewType(int position) {
        if (pesan.get(position).isSendByUser)  return 1;
        else return 0;
    }

    @NonNull
    @Override
    public Adapter_Chat_Input.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("Errors", "data Tidak null " + pesan.size());
        View v ;
        if(viewType==1) v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chat_layout_user,parent,false);
        else v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chat_layout_andromaid,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Chat_Input.ViewHolder holder, int position) {
        holder.messageText.setText(pesan.get(position).Content);
        holder.timeText.setText(pesan.get(position).time);
    }

    @Override
    public int getItemCount() {
        return pesan.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView messageText, timeText;
        public ViewHolder(View view)
        {
            super(view);
            messageText = view.findViewById(R.id.text_message_body);
            timeText = view.findViewById(R.id.text_message_time);
        }
    }
}
