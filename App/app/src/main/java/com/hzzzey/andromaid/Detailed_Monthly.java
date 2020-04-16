package com.hzzzey.andromaid;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import androidx.appcompat.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Detailed_Monthly extends AppCompatActivity {

    SpendingDb dbHelper = SpendingDb.getInstance(this);
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
// set an exit transition
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed__monthly);
        LineChart mLinechart;
        setContentView(R.layout.activity_detailed__monthly);
        List<Entry> lineEntries = new ArrayList<>();
        mLinechart = findViewById(R.id.MonthlySpendingDetailed);
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
            lineEntries.add(new BarEntry(data,(float) itemSizes.get(i)));
//            xAxisLables.add(i+"");
        }
        //TODO perbaiki Chart
        LineDataSet lineDataSet = new LineDataSet(lineEntries,"Spending");
        lineDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        LineData lineData = new LineData((lineDataSet));

        mLinechart.setVisibility(View.VISIBLE);
        mLinechart.getXAxis().setDrawGridLines(false);
//        mLinechart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLables));
        mLinechart.getAxisRight().setDrawGridLines(false);
        mLinechart.getAxisRight().setEnabled(false);
        mLinechart.animateY(2000);
        mLinechart.setData(lineData);
        mLinechart.setTouchEnabled(true);
        Description desc= new Description();
        desc.setText("This month spending");
        mLinechart.setDescription(desc);
        mLinechart.invalidate();
    }

}
