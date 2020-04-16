package com.hzzzey.andromaid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.BaseColumns;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class this_month_money_flows extends Fragment {
    private BarChart mBarchart;
    private OnFragmentInteractionListener mListener;

    SpendingDb dbHelper = SpendingDb.getInstance(getContext());
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    public this_month_money_flows() {
        // Required empty public constructor
    }
    public static this_month_money_flows newInstance() {
        this_month_money_flows fragment = new this_month_money_flows();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_this_month_money_flows, container, false);
        mBarchart = view.findViewById(R.id.MonthlybarChart);
        fillchardata();
        return view;
    }
    public void fillchardata()
    {
        //TODO perbaiki Chart

        List<BarEntry> barEntries = new ArrayList<>();

        List itemSizes = new ArrayList<>();
        List days = new ArrayList<String>();

        Calendar cal = Calendar.getInstance();

        Date temp = cal.getTime();
        Log.d("KSqlite",temp.toString());
        String Day_Month = (String) DateFormat.format("dd",   temp); // date
        if (Day_Month.equals("01"))//apabila ini awal dari bulan
        {
            Log.d("KSqlite","FBK");
            cal.setTime(cal.getTime());
            cal.add(Calendar.MONTH,-1);//mendapat bulan lalu
            temp = cal.getTime();
        }
        String monthNumber= (String) DateFormat.format("MM",   temp);//month
        String yearNumber= (String) DateFormat.format("yyyy",   temp);//month
        String Month_yearCode = monthNumber +'-'+yearNumber ;

        Log.d("KSqlite",Month_yearCode);


        Cursor cursors = db.rawQuery("Select SUM(Spending_Amount) as total,strftime('%d',datetime(Spending_Date_Time,'localtime')) as day," +
                "strftime('%d-%m-%Y',datetime(Spending_Date_Time,'localtime') ) as ValMonth " +
                "from Spending where strftime('%m-%Y',datetime(Spending_Date_Time,'localtime')) ='"+ Month_yearCode +"'  group by  strftime('%d-%m-%Y',datetime(Spending_Date_Time,'localtime'))" ,null);
        //todo lihat yang di '04-2020' cari cara agar bulannya dinamis
        while(cursors.moveToNext()) {
            Float itemSize = Float.parseFloat(cursors.getString(cursors.getColumnIndexOrThrow("total")));
            String day = cursors.getString(cursors.getColumnIndexOrThrow("day"));
            Log.d("KSqlite", day);
            days.add(day);
            itemSizes.add(itemSize);
        }
        cursors.close();
        for (int i=0;i<itemSizes.size();i++)
        {
            int data = Integer.parseInt((String) days.get(i));
            barEntries.add(new BarEntry(data,(float) itemSizes.get(i)));
        }
        BarDataSet barDataSet = new BarDataSet(barEntries,"Spending");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData = new BarData((barDataSet));
        barData.setDrawValues(false);
        mBarchart.setVisibility(View.VISIBLE);
        mBarchart.getAxisLeft().setDrawGridLines(false);
        mBarchart.getXAxis().setDrawGridLines(false);
//        mBarchart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLables));
        mBarchart.getAxisRight().setDrawGridLines(false);
        mBarchart.getAxisRight().setEnabled(false);
        mBarchart.animateY(2000);
        mBarchart.setData(barData);
        mBarchart.setTouchEnabled(false);
        Description desc= new Description();
        desc.setText("This month spending");
        mBarchart.setDescription(desc);
        mBarchart.invalidate();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
