package com.example.met1.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.met1.R;
public class settingsFragment extends Fragment {
    Button logout;

    public settingsFragment() {
        // Required empty public constructor
    }

    public static settingsFragment newInstance(String param1, String param2) {
        settingsFragment fragment = new settingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        logout = view.findViewById(R.id.logoutBtn);
        SessionManager sessionManager = new SessionManager(getContext());
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Activity MainActivity = getActivity();
                    sessionManager.logoutUser();
                    startActivity(new Intent(getContext(), Login.class));
                    MainActivity.finish();

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return view;
    }
}