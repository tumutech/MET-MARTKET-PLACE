package com.example.met1.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.met1.R;

public class Admin extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
       // getSupportFragmentManager().beginTransaction().add(R.id.container,new NewProduct()).commit();
    }
}
