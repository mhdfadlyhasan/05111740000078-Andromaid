package com.hzzzey.andromaid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Money_Fragment extends Fragment {
    View fragment_monthly;
    View fragment_money_information;
    private Money_Fragment_Listener listener;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.money, container, false);
        fragment_monthly =  root.findViewById(R.id.user_monthly_spending);
        fragment_money_information =  root.findViewById(R.id.fragment_money_information);
        fragment_money_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),Detailed_Daily_and_Surplus_money.class),0);
            }
        });
        fragment_monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Detailed_Monthly.class));
            }
        });

        return root;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (Money_Fragment_Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Implement the listener!");
        }
    }

    public int NeedRefresh = 1;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == NeedRefresh)
        {
            listener.Refresh();
        }
    }
    public interface Money_Fragment_Listener
    {
        void Refresh();
    }
}