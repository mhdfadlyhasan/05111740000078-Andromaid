package com.hzzzey.andromaid;

import android.provider.BaseColumns;



public final class SpendingContract
{
    private SpendingContract(){}

    public final class SpendingEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "Spending";
        public static final String SPENDING_AMOUNT = "Spending_Amount";
        public static final String SPENDING_DATE_TIME = "Spending_Date_Time";

    }

}