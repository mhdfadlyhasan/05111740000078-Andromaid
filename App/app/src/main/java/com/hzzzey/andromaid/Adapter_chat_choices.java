package com.hzzzey.andromaid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_chat_choices extends RecyclerView.Adapter<Adapter_chat_choices.ViewHolder>{
    List<BaseChoice> choices;
    private OnChoiceListener onChoiceListener;
    public Adapter_chat_choices(List list,OnChoiceListener onChoiceListener)
    {
        this.choices = list;
        this.onChoiceListener = onChoiceListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_choices,parent,false);
        return new ViewHolder(v,onChoiceListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.messageText.setText(choices.get(position).Content+"");
    }
    @Override
    public int getItemCount() {
        return choices.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView messageText;
        OnChoiceListener onChoiceListener;
        public ViewHolder(View view, OnChoiceListener onChoiceListener)
        {
            super(view);
            messageText = view.findViewById(R.id.choice_message_body);
            view.setOnClickListener(this);
            this.onChoiceListener = onChoiceListener;
        }

        @Override
        public void onClick(View v) {
            onChoiceListener.onChoiceClick(getAdapterPosition());
        }
    }
    public interface OnChoiceListener{
        void onChoiceClick(int position);
    }
}
