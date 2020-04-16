package com.hzzzey.andromaid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Constraints;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class today_moneyflow_fragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    User user = User.getInstance();
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_today_spending_fragment, container, false);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

        // specify an adapter (see also next example)
        List Spending = new ArrayList<String>();
        List Time = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        SpendingDb dbHelper = SpendingDb.getInstance(this.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Date temp = cal.getTime();
        String Day_Month = (String) DateFormat.format("dd", temp); // date
        String monthNumber = (String) DateFormat.format("MM", temp);// month
        String yearNumber = (String) DateFormat.format("yyyy", temp);// Year
        String Day_Month_yearCode = Day_Month + '-' + monthNumber + '-' + yearNumber;
        Cursor cursors = db.rawQuery("Select strftime('%H.%M',datetime(Spending_Date_Time,'localtime')) as Time ," +
                "Spending_Amount " +
                "from Spending where " +
                "strftime('%d-%m-%Y',datetime(Spending_Date_Time,'localtime')) = '" + Day_Month_yearCode + "' ORDER BY _ID DESC", null);
        //todo diurutkan berdasarkan waktu

        while (cursors.moveToNext()) {
            String spend = cursors.getString(cursors.getColumnIndexOrThrow("Spending_Amount"));
            String time = cursors.getString(cursors.getColumnIndexOrThrow("Time"));

            Spending.add(spend);//menambbahkan spending
            Time.add(time);
            Log.d("KSqlite", spend + " " + time);
        }
        cursors = db.rawQuery("Select SUM(Spending_Amount) as total " +
                "from Spending where " +
                "strftime('%d-%m-%Y',datetime(Spending_Date_Time,'localtime')) = '" + Day_Month_yearCode + "' ", null);
        String Total = null;
        if (cursors.moveToFirst()) {
            Total = cursors.getString(cursors.getColumnIndexOrThrow("total"));
            Log.d("Eror",Total +" ini total");
        }
        if (Total == null) Total = "0";
        int  maxDay= cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        Long Alokasi_Today = (long)user.get_MonthlyMoney()/maxDay - Long.parseLong(Total);
        Log.d("KSqlite ", Total + " Adalah " + Alokasi_Today);
        cursors.close();
        TextView today_spending_title = root.findViewById(R.id.todays_money_left);

        today_spending_title.setText(Alokasi_Today+"");

        recyclerView = root.findViewById(R.id.recyclerview_today_spending);

        recyclerView.setHasFixedSize(true);

        mAdapter = new Adapter_today_spending(Spending, Time);

        recyclerView.setAdapter(mAdapter);

        layoutManager = new LinearLayoutManager(root.getContext());

        recyclerView.setLayoutManager(layoutManager);

        return root;
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
