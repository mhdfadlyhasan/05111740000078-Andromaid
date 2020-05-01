package com.hzzzey.andromaid;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class User {
    private static final User ourInstance = new User();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor EditorSharedPref;
    private Context context;
    Calendar cal;
    SpendingDb dbHelper;
    SQLiteDatabase db;
    Cursor cursors;
    public static User getInstance() {
        return ourInstance;
    }
    private User() {

    }
    public void init(Context context)
    {
        if(sharedPreferences==null){
            this.context = context;
            sharedPreferences = context.getSharedPreferences(context.getPackageName()+"_Prefences" , Activity.MODE_PRIVATE);
            EditorSharedPref = sharedPreferences.edit();
            cal = Calendar.getInstance();
            dbHelper = SpendingDb.getInstance(context);
            db = dbHelper.getWritableDatabase();
        }
    }
    public void set_MonthlyMoney(double newInfo)
    {
        EditorSharedPref.putLong("Andromaid_Monthly_money_sharedPrefrenceKey", Double.doubleToRawLongBits(newInfo)).apply();
    }

    public void set_LeftMoney(double newInfo)
    {
        EditorSharedPref.putLong("Andromaid_Left_money_sharedPrefrenceKey",Double.doubleToRawLongBits(newInfo)).apply();
    }

    public void set_UserName(String newInfo)
    {
        EditorSharedPref.putString("Andromaid_User_name_money_sharedPrefrenceKey",newInfo).apply();
    }
    public void spend(Double newInfo)
    {
        ContentValues values = new ContentValues();
        values.put(SpendingContract.SpendingEntry.SPENDING_AMOUNT, newInfo);
        db.insert(SpendingContract.SpendingEntry.TABLE_NAME,null,values);
        Double newValue = this.get_LeftMoney() - newInfo;
        Log.d("inputdata",newValue +"");
        set_LeftMoney(newValue);
    }

    public double get_MonthlyMoney()
    {
        return Double.longBitsToDouble(sharedPreferences.getLong("Andromaid_Monthly_money_sharedPrefrenceKey", Double.doubleToLongBits(0.01)));
    }

    public double get_LeftMoney()
    {
        return Double.longBitsToDouble(sharedPreferences.getLong("Andromaid_Left_money_sharedPrefrenceKey", Double.doubleToLongBits(0.01)));
    }

    public Long getSurplusMoney()
    {
        int MonthLength = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        int thisdate = Integer.parseInt(dateFormat.format(cal.getTime()));
        Long leftToday = getLeftToday();
        if (leftToday<0) leftToday*=0;
        Long SurplusMoney = (long) (get_LeftMoney() - (getAlocation() * ( MonthLength - thisdate ) + leftToday));
        return SurplusMoney;

    }

    public List<monthlySpending> getMonthlySpending()
    {
        List Spending = new ArrayList<monthlySpending>();
        Date temp = cal.getTime();
        String Day_Month = (String) DateFormat.format("dd",   temp); // date
        if (Day_Month.equals("01"))//apabila ini awal dari bulan
        {
            cal.setTime(cal.getTime());
            cal.add(Calendar.MONTH,-1);//mendapat bulan lalu
            temp = cal.getTime();
        }
        String monthNumber= (String) DateFormat.format("MM",   temp);//month
        String yearNumber= (String) DateFormat.format("yyyy",   temp);//month
        String Month_yearCode = monthNumber +'-'+yearNumber ;
        Cursor cursors = db.rawQuery("Select SUM(Spending_Amount) as total,strftime('%d',datetime(Spending_Date_Time,'localtime')) as day," +
                "strftime('%d-%m-%Y',datetime(Spending_Date_Time,'localtime') ) as ValMonth " +
                "from Spending where strftime('%m-%Y',datetime(Spending_Date_Time,'localtime')) ='"+ Month_yearCode +"'  group by  strftime('%d-%m-%Y',datetime(Spending_Date_Time,'localtime'))" ,null);

        while(cursors.moveToNext()) {
            Float spending = Float.parseFloat(cursors.getString(cursors.getColumnIndexOrThrow("total")));
            String day = cursors.getString(cursors.getColumnIndexOrThrow("day"));
            Log.d("KSqlite", day);
            Spending.add(new monthlySpending(spending,day));
        }
        cursors.close();
        cal = Calendar.getInstance();
        return Spending;
    }
    public String get_UserName()
    {
        return sharedPreferences.getString("Andromaid_User_name_money_sharedPrefrenceKey","Non");
    }
    public List<todayspending> getTodaySpending()
    {
        List Spending = new ArrayList<todayspending>();
        Date temp = cal.getTime();
        String Day_Month = (String) DateFormat.format("dd", temp); // date
        String monthNumber = (String) DateFormat.format("MM", temp);// month
        String yearNumber = (String) DateFormat.format("yyyy", temp);// Year
        String Day_Month_yearCode = Day_Month + '-' + monthNumber + '-' + yearNumber;
        cursors = db.rawQuery("Select strftime('%H.%M',datetime(Spending_Date_Time,'localtime')) as Time ," +
                "Spending_Amount " +
                "from Spending where " +
                "strftime('%d-%m-%Y',datetime(Spending_Date_Time,'localtime')) = '" + Day_Month_yearCode + "' ORDER BY _ID DESC", null);
        while (cursors.moveToNext()) {
            String spend = cursors.getString(cursors.getColumnIndexOrThrow("Spending_Amount"));
            String time = cursors.getString(cursors.getColumnIndexOrThrow("Time"));
            Spending.add(new todayspending(spend,time));//menambahkan spending
        }
        cursors.close();
        return  Spending;
    }


    public String getTotalToday()
    {
        Date temp = cal.getTime();

        String Day_Month_year_code = (String) DateFormat.format("dd-MM-yyyy", temp) ; // date

        cursors = db.rawQuery("Select SUM(Spending_Amount) as total " +
                "from Spending where " +
                "strftime('%d-%m-%Y',datetime(Spending_Date_Time,'localtime')) = '" + Day_Month_year_code + "' ", null);
        String Total = null;
        if (cursors.moveToFirst()) {
            Total = cursors.getString(cursors.getColumnIndexOrThrow("total"));
        }
        cursors.close();
        if (Total == null) Total = "0";
        return Total;
    }
    public Long getAlocation()
    {
        int  maxDay= cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        Long Alokasi_Today = (long)get_MonthlyMoney()/maxDay;
        return Alokasi_Today;
    }
    public Long getLeftToday()
    {
        String Total = getTotalToday();
        Long Alokasi_Today = getAlocation() - Long.parseLong(Total);
        return Alokasi_Today;
    }
}
