package com.hzzzey.andromaid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InputDataDummy extends AppCompatActivity {
    private User user = User.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data_dummy);
    }
    public void onClick(View v)
    {
        EditText data;
        switch (v.getId())
        {
            case R.id.buttonSendName:
                data = findViewById(R.id.NameInputBox);
                user.set_UserName(data.getText().toString());
                setResult(1,null);
                break;
            case R.id.buttonAddSpending:
                data = findViewById(R.id.SpendingInput);
                user.spend(Double.parseDouble(data.getText().toString()));
                setResult(1,null);
                break;
            case R.id.buttonSetMonthly:
                data = findViewById(R.id.MonthlyInput);
                user.set_MonthlyMoney(Double.parseDouble(data.getText().toString()));
                setResult(1,null);
                break;
        }
    }
}
