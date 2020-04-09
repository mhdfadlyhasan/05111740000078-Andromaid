package com.hzzzey.andromaid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter_today_spending extends RecyclerView.Adapter<Adapter_today_spending.ViewHolder> {

    private int[] mDataset;
    public class ViewHolder extends  RecyclerView.ViewHolder{
//todo masih belum ada informasi recycler
        TextView TextSpending;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TextSpending = itemView.findViewById(R.id.layout_today_spending_spends_information);
        }
    }
    public Adapter_today_spending(int[] myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override

    public Adapter_today_spending.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewobject = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_today_spending,parent,false);
        return new ViewHolder(viewobject);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_today_spending.ViewHolder holder, int position) {
        holder.TextSpending.setText(position+"");
    }

    @Override
    public int getItemCount() {
        return 30;
    }

}
