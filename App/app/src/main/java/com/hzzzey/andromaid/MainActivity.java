package com.hzzzey.andromaid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.hzzzey.andromaid.SpendingContract.SpendingEntry;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hzzzey.andromaid.ui.your_money_information_and_surplus_money;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, Money_Fragment.Money_Fragment_Listener,
        PopUpRename.PopUpRenameListener,this_month_money_flows.OnFragmentInteractionListener,
        your_money_information_and_surplus_money.OnFragmentInteractionListener
{
    View dragView;
    float dX;
    float dY;
    int lastAction;
    //todo ini cara set db
    SpendingDb dbHelper = SpendingDb.getInstance(this);
    private User user = User.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user.init(this);//ini fungsi untuk memasang context di shared prefrence, ntah kenapa harus ada
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_Money)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        dragView = findViewById(R.id.draggable_view);
        Toast.makeText(this,user.get_UserName()+" "+user.get_MonthlyMoney(),Toast.LENGTH_SHORT).show();
        dragView.setOnTouchListener(this);
    }
    @Override
    public void onFragmentInteraction(Uri uri){

    }

    public void insert_spending(Double Angka)
    {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 6);// for 6 hour
        calendar.set(Calendar.DAY_OF_MONTH, 4);
        calendar.set(Calendar.MINUTE, 0);// for 0 min
        calendar.set(Calendar.SECOND, 0);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SpendingEntry.SPENDING_AMOUNT, Angka+"");
        long newRowId = db.insert(SpendingEntry.TABLE_NAME,null,values);
        Toast.makeText(this,newRowId+"",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.all_side_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();

        switch (item.getItemId())
        {
            case R.id.CallnameChange:
                PopUpRename popUpRenames = new PopUpRename();
                popUpRenames.show(manager,"ok");
                return true;
            case R.id.about:
                startActivity(new Intent(this,About.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeName(String Name) {
        user.set_UserName(Name);
        Refresh();
    }
    public void Refresh() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                lastAction = MotionEvent.ACTION_DOWN;
                break;

            case MotionEvent.ACTION_MOVE:
                view.setY(event.getRawY() + dY);
                view.setX(event.getRawX() + dX);
                lastAction = MotionEvent.ACTION_MOVE;
                break;

            case MotionEvent.ACTION_UP:
                if (lastAction == MotionEvent.ACTION_DOWN)
                    startActivityForResult(new Intent(this,ChatInput.class),0);
                break;
            default:
                return false;
        }
        return true;
    }

    public int NeedRefresh = 1;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == NeedRefresh)
        {
            Refresh();
        }
    }
}