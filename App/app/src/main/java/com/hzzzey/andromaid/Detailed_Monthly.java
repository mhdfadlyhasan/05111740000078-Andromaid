package com.hzzzey.andromaid;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class Detailed_Monthly extends AppCompatActivity {
    User user = User.getInstance();

    LineChart mLinechart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
// set an exit transition
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed__monthly);
        setContentView(R.layout.activity_detailed__monthly);
        List<Entry> lineEntries = new ArrayList<>();
        mLinechart = findViewById(R.id.MonthlySpendingDetailed);
        List<monthlySpending> spending = user.getMonthlySpending();
        for (int i=0;i<spending.size();i++)
        {
            int data = Integer.parseInt( spending.get(i).time);
            lineEntries.add(new BarEntry(data, spending.get(i).spending));
        }
        initSettings(lineEntries);
    }

    private void initSettings(List lineEntries)
    {
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
