package com.hzzzey.andromaid;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Debug;
import android.util.Log;

public class User {
    private static final User ourInstance = new User();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor EditorSharedPref;

    public static User getInstance() {
        return ourInstance;
    }
    private User() {

    }
    public void init(Context context)
    {
        if(sharedPreferences==null){

            sharedPreferences = context.getSharedPreferences(context.getPackageName()+"_Prefences" , Activity.MODE_PRIVATE);
            EditorSharedPref = sharedPreferences.edit();
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

    public String get_UserName()
    {
        return sharedPreferences.getString("Andromaid_User_name_money_sharedPrefrenceKey","Non");
    }
}
