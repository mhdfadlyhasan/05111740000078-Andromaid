package com.hzzzey.andromaid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//todo isi kontent, dan perbaikin tampilan, data juga belum ada
public class Detailed_Daily_and_Surplus_money extends AppCompatActivity implements DialogEditMonthly.DialogEditMonthlyListener, editLeft.DialogEditLeftListener {
    User user = User.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed__daily_and__surplus_money);
        Button button = findViewById(R.id.YesEdit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
                DialogEditMonthly editMonthly = new DialogEditMonthly();
                editMonthly.show(manager,"ok");
            }
        });
        Button buttonMoneyLeft = findViewById(R.id.yesLeft);
        buttonMoneyLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
                editLeft editLeft = new editLeft();
                editLeft.show(manager,"ok");
            }
        });

        TextView textView = findViewById(R.id.MoneyText);
        String Pesan = "For this month, you allocated " + user.get_MonthlyMoney() +
                " for this month\nSo... Until Today, You should Have\n" + user.get_LeftMoney() +
                " until the end of the month which is \n"               + user.getAlocation() +
                " for your daily spending \n and "                      + user.getSurplusMoney() +
                " as Surplus Spending, go ahead to use them or save them";
        textView.setText(Pesan);
    }
    public void MonthlyChangeIsYes(Double newMoney)
    {
        user.set_MonthlyMoney(newMoney);
        Intent intent = new Intent();
        startActivity(getIntent());
        setResult(1,intent);
        finish();
    }
    public void LeftChangeIsYes(Double newMoney)
    {
        user.set_LeftMoney(newMoney);
        Intent intent = new Intent();
        startActivity(getIntent());
        setResult(1,intent);
        finish();

    }
}
