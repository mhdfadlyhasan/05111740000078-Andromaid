package com.hzzzey.andromaid;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Money_Fragment extends Fragment {
    View fragment_monthly;
    View fragment_money_information;
    User user = User.getInstance();
    private Money_Fragment_Listener listener;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.money, container, false);
        root.findViewById(R.id.user_money_information);
        fragment_monthly =  root.findViewById(R.id.user_monthly_spending);
        fragment_money_information =  root.findViewById(R.id.user_money_information);
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
        TextView dailyMoney = root.findViewById(R.id.daily_money_text);
        TextView surplusMoney = root.findViewById(R.id.surplus_money_text);
        Log.d("moneyinfo ",dailyMoney.getText().toString());

        //todo set daily dan surplus fragment disini
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        Calendar calendar = Calendar.getInstance();

        int thisdate = Integer.parseInt(dateFormat.format(calendar.getTime()));
        int MonthLength = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        Calendar cal = Calendar.getInstance();
        SpendingDb dbHelper = SpendingDb.getInstance(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Date temp = cal.getTime();

        String Day_Month = (String) DateFormat.format("dd", temp); // date
        String monthNumber = (String) DateFormat.format("MM", temp);// month
        String yearNumber = (String) DateFormat.format("yyyy", temp);// Year
        String Day_Month_yearCode = Day_Month + '-' + monthNumber + '-' + yearNumber;
        Cursor cursors = db.rawQuery("Select SUM(Spending_Amount) as total " +
                "from Spending where " +
                "strftime('%d-%m-%Y',datetime(Spending_Date_Time,'localtime')) = '" + Day_Month_yearCode + "' ", null);
        String Total = null;
        if (cursors.moveToFirst()) {
            Total = cursors.getString(cursors.getColumnIndexOrThrow("total"));
        }
        if (Total == null) Total = "0";
        cursors.close();
        Long DataDailyMoney = (long) user.get_MonthlyMoney()/MonthLength ;
        Long Alokasi_Today = DataDailyMoney - Long.parseLong(Total);
        if (Alokasi_Today<0) Alokasi_Today*=0;
        Long SurplusMoney = (long) user.get_LeftMoney() - (DataDailyMoney * ( MonthLength - thisdate ) + Alokasi_Today);
        DataDailyMoney = (long) user.get_MonthlyMoney()/MonthLength ;
        dailyMoney.setText(DataDailyMoney.toString()); //daily money
        surplusMoney.setText(SurplusMoney.toString()); //surplus money
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