package com.example.met1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.met1.Adapter.FoodListAdapter;
import com.example.met1.Domain.ProductDomain;
import com.example.met1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

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
//        getSupportFragmentManager().beginTransaction().add(R.id.homecontainer, new UserHomeFragment()).commit();
//        getFragmentManager().beginTransaction().add(R.id.homecontainer,new UserHomeFragment()).commit();


//        bottomNavigation();

    }

//    private void bottomNavigation() {
////        LinearLayout homeBtn=findViewById(R.id.homeBtn);
////        LinearLayout cartBtn=findViewById(R.id.cartBtn);
////        LinearLayout settings = findViewById(R.id.settings);
//
////        homeBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,MainActivity.class)));
////
////        cartBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,CartActivity.class)));
//
//    }



    UserHomeFragment userHomeFragment=new UserHomeFragment();
    ProfileFragment profileFragment=new ProfileFragment();
    CartFragment cartFragment = new CartFragment();
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, userHomeFragment)
                        .commit();
                return true;

            case R.id.profile:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, profileFragment)
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