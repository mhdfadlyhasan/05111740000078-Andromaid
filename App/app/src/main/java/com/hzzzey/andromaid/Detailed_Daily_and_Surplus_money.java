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
        String Pesan = "For this month, you allocated " + 120.000 +" for this month\nSo... Until Today, You should Have\n" + 180.000 + " until the end of the month which is \n"
                + 120.000 + " for your daily spending" +
                "\n and " + 60.000 + " as Surplus Spending, go ahead to use them or save them";
        textView.setText(Pesan);
    }
    public void MonthlyChangeIsYes(Double newMoney)
    {
        Intent intent = new Intent();
        startActivity(getIntent());
        setResult(1,intent);
        finish();
        //todo ubah monthly money disini!
    }
    public void LeftChangeIsYes(Double newMoney)
    {
        Intent intent = new Intent();
        startActivity(getIntent());
        setResult(1,intent);
        finish();
        //todo ubah Left money disini!
    }
}
