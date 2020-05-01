package com.hzzzey.andromaid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
public class Fragment_Money_information extends Fragment {
    User user = User.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_your_money_information_and_surplus_money,container,false);
        TextView dailyMoney = root.findViewById(R.id.daily_money_text);
        TextView surplusMoney = root.findViewById(R.id.surplus_money_text);
        dailyMoney.setText(user.getAlocation()+""); //daily money
        surplusMoney.setText(user.getSurplusMoney()+""); //surplus money
        return root;
    }

}
