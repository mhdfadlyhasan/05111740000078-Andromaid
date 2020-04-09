package com.hzzzey.andromaid.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzzzey.andromaid.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link your_money_information_and_surplus_money.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link your_money_information_and_surplus_money#newInstance} factory method to
 * create an instance of this fragment.
 */
public class your_money_information_and_surplus_money extends Fragment {

    private OnFragmentInteractionListener mListener;

    public your_money_information_and_surplus_money() {
        // Required empty public constructor
    }

    public static your_money_information_and_surplus_money newInstance(String param1, String param2) {
        your_money_information_and_surplus_money fragment = new your_money_information_and_surplus_money();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        return inflater.inflate(R.layout.fragment_your_money_information_and_surplus_money, container, false);
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
