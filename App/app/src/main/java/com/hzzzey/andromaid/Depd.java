//package com.hzzzey.andromaid;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import static com.hzzzey.andromaid.R.layout.fragment_your_money_information_and_surplus_money;
//
//public class FragmentYourMoneyInformationAndSurplusMoney extends Fragment {
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View root = inflater.inflate(fragment_your_money_information_and_surplus_money, container, false);
//        TextView dailyMoney = root.findViewById(R.id.daily_money_text);
//        TextView surplusMoney = root.findViewById(R.id.surplus_money_text);
//        Log.d("moneyinfo",dailyMoney.getText().toString());
//        surplusMoney.setText("20000");
//        return root;
//    }
//}
