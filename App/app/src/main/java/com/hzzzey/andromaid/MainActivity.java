package com.hzzzey.andromaid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hzzzey.andromaid.ui.your_money_information_and_surplus_money;


public class MainActivity extends AppCompatActivity implements  Money_Fragment.Money_Fragment_Listener,
        PopUpRename.PopUpRenameListener,this_month_money_flows.OnFragmentInteractionListener, your_money_information_and_surplus_money.OnFragmentInteractionListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }
    @Override
    public void onFragmentInteraction(Uri uri){

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

    @Override
    public void changeName(String Name) {
        Toast.makeText(this,Name,Toast.LENGTH_SHORT).show();
        //TODO tambahkan fungsi yang ubah nama;
        Refresh();
    }
    public void Refresh() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(this,"Refreshing",Toast.LENGTH_SHORT).show();
    }
}