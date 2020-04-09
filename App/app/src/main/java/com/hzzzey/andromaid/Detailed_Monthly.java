package com.hzzzey.andromaid;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.transition.Explode;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

public class Detailed_Monthly extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
// set an exit transition
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed__monthly);

        LineChart mLinechart;
        setContentView(R.layout.activity_detailed__monthly);
        List<Entry> lineEntries = new ArrayList<>();
        mLinechart = findViewById(R.id.MonthlySpendingDetailed);
        for (int i=1;i<31;i++)
        {
            lineEntries.add(new BarEntry(i,10000*i));
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
        mLinechart.setTouchEnabled(false);
        Description desc= new Description();
        desc.setText("This month spending");
        mLinechart.setDescription(desc);
        mLinechart.invalidate();
    }

}
