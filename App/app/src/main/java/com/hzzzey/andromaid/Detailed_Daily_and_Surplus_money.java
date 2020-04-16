package com.hzzzey.andromaid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        int thisdate = Integer.parseInt(dateFormat.format(calendar.getTime()));
        int MonthLength = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        Calendar cal = Calendar.getInstance();
        SpendingDb dbHelper = SpendingDb.getInstance(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Date temp = cal.getTime();

        String Day_Month = (String) DateFormat.format("dd", temp); // date
        String monthNumber = (String) DateFormat.format("MM", temp);// month
        String yearNumber = (String) DateFormat.format("yyyy", temp);// Year
        String Day_Month_yearCode = Day_Month + '-' + monthNumber + '-' + yearNumber;

        Cursor cursors = db.rawQuery("Select SUM(Spending_Amount) as total " +
                "from Spending where " +
                "strftime('%d-%m-%Y',datetime(Spending_Date_Time,'localtime')) = '" + Day_Month_yearCode + "' ", null);

        String Total = null;
        if (cursors.moveToFirst()) {
            Total = cursors.getString(cursors.getColumnIndexOrThrow("total"));
        }
        if (Total == null) Total = "0";

        cursors.close();
        Long DataDailyMoney = (long) user.get_MonthlyMoney()/MonthLength ;

        Long Alokasi_Today = DataDailyMoney - Long.parseLong(Total);
        if (Alokasi_Today<0) Alokasi_Today*=0;
        Long SurplusMoney = (long) user.get_LeftMoney() - (DataDailyMoney * ( MonthLength - thisdate ) + Alokasi_Today);


        TextView textView = findViewById(R.id.MoneyText);
        String Pesan = "For this month, you allocated " + user.get_MonthlyMoney() +
                " for this month\nSo... Until Today, You should Have\n" + user.get_LeftMoney() +
                " until the end of the month which is \n"
                + DataDailyMoney + " for your daily spending" +
                "\n and " + SurplusMoney + " as Surplus Spending, go ahead to use them or save them";
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
