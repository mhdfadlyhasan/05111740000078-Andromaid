package com.hzzzey.andromaid;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
        List Spending;
        Spending = user.getTodaySpending();
        Long left_today = user.getLeftToday();
        TextView today_spending_title = root.findViewById(R.id.todays_money_left);

        today_spending_title.setText(left_today+"");

        recyclerView = root.findViewById(R.id.recyclerview_today_spending);

        recyclerView.setHasFixedSize(true);

        mAdapter = new Adapter_today_spending(Spending);

        recyclerView.setAdapter(mAdapter);

        layoutManager = new LinearLayoutManager(root.getContext());

        recyclerView.setLayoutManager(layoutManager);

        return root;
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
