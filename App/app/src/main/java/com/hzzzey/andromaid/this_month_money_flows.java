package com.hzzzey.andromaid;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class this_month_money_flows extends Fragment {
    private BarChart mBarchart;
    private OnFragmentInteractionListener mListener;

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
//        ArrayList<String> xAxisLables = new ArrayList();
        List<BarEntry> barEntries = new ArrayList<>();
        for (int i=1;i<31;i++)
        {
            barEntries.add(new BarEntry(i,10000*i));
//            xAxisLables.add(i+"");
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
