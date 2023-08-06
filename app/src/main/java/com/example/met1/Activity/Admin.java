package com.example.met1.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.met1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Admin extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    UserHomeFragment userHomeFragment= new UserHomeFragment();
    CartFragment cartFragment= new CartFragment();
    ProfileFragment profileFragment= new ProfileFragment();
    AddProductFragment addProductFragment= new AddProductFragment();


    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        bottomNavigationView= findViewById(R.id.bottomNav);

        //Code for displaying the home fragment first
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, addProductFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, userHomeFragment).commit();
                        return true;
                    case R.id.cart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, cartFragment).addToBackStack(null).commit();
                        return true;
//                    case R.id.notification:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, notificationFragment).addToBackStack(null).commit();
//                        return true;
//                    case R.id.account:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, accountFragment).addToBackStack(null).commit();
//                        return true;
                }
                return false;
            }
        });


    }
}
