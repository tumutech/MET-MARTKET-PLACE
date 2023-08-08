package com.example.met1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.met1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements  BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView=findViewById(R.id.bottomNav);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, userHomeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    UserHomeFragment userHomeFragment=new UserHomeFragment();
    ProfileFragment profileFragment=new ProfileFragment();
    settingsFragment settingFragment = new settingsFragment();
    CartFragment cartFragment = new CartFragment();
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, userHomeFragment)
                        .commit();
                return true;

            case R.id.settings:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, settingFragment)
                        .commit();
                return true;
            case R.id.cart:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, cartFragment)
                        .commit();
                return true;
        }
        return true;
    }
}